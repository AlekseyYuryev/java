/* Copyright (c) 2015 Seva Safris
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * You should have received a copy of The MIT License (MIT) along with this
 * program. If not, see <http://opensource.org/licenses/MIT/>.
 */

package org.safris.maven.plugin.cert;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.maven.DuplicateProjectException;
import org.apache.maven.MavenExecutionException;
import org.safris.commons.lang.Pair;

public class POMFile extends ModuleId {
  private static final Map<ModuleId,POMFile> pomFiles = new HashMap<ModuleId,POMFile>();

  public static Collection<UpdateCommand> getPendingUpdates() {
    final Set<UpdateCommand> updates = new LinkedHashSet<UpdateCommand>();
    for (final POMFile pomFile : pomFiles.values())
      updates.addAll(pomFile.updates);

    return updates;
  }

  public static POMFile parse(final File file, final String text) throws IOException, MavenExecutionException {
    return parse(file, text, "project");
  }

  public static POMFile lookup(final ModuleId moduleId) {
    return pomFiles.get(moduleId);
  }

  private static POMFile makeFromParent(final File dir, final String text) throws IOException, MavenExecutionException {
    final ModuleId parentId = parseModuleId(text, "project/parent");
    if (parentId == null)
      return null;

    final int[][] relativePathIndex = VersionUtil.indexOfTag(text, "project/parent/relativePath");
    final File file = new File(dir, relativePathIndex != null ? text.substring(relativePathIndex[0][0], relativePathIndex[0][1]).trim() : "../pom.xml");
    final POMFile pomFile = parse(file, new String(Files.readAllBytes(file.toPath())), "project");
    return ModuleId.equal(parentId, pomFile) ? pomFile : null;
  }

  private static ModuleId parseModuleId(final String text, final String scope) {
    final int[][] artifactIdIndex = VersionUtil.indexOfTag(text, scope + "/artifactId");
    if (artifactIdIndex == null)
      return null;

    final String artifactId = text.substring(artifactIdIndex[0][0], artifactIdIndex[0][1]).trim();

    final int[][] groupIdIndex = VersionUtil.indexOfTag(text, scope + "/groupId");
    final String groupId = groupIdIndex == null ? null : text.substring(groupIdIndex[0][0], groupIdIndex[0][1]).trim();

    final int[][] versionIndex = VersionUtil.indexOfTag(text, scope + "/version");
    final String version = versionIndex == null ? null : text.substring(versionIndex[0][0], versionIndex[0][1]).trim();
    return new ModuleId(groupId, artifactId, version);
  }

  private static void checkSame(final ModuleId moduleId, final POMFile a, final POMFile b) throws IOException, MavenExecutionException {
    if (!a.file.getCanonicalPath().equals(b.file.getCanonicalPath()))
      throw new DuplicateProjectException("Identical module IDs.", Collections.singletonMap(moduleId.toString(), Arrays.asList(a.file, b.file)));
  }

  private static POMFile parse(final File file, final String text, final String scope) throws IOException, MavenExecutionException {
    final ModuleId moduleId = parseModuleId(text, scope);

    POMFile prototype = new POMFile(file, text, moduleId.groupId(), moduleId.artifactId(), moduleId.version());
    POMFile instance = pomFiles.get(moduleId);
    if (instance != null) {
      POMFile.checkSame(moduleId, prototype, instance);
      return instance;
    }

    synchronized (pomFiles) {
      instance = pomFiles.get(prototype);
      if (instance != null) {
        POMFile.checkSame(moduleId, prototype, instance);
        return instance;
      }

      pomFiles.put(moduleId, prototype);
      return prototype;
    }
  }

  private static POMFile getParent(final File file, final String text) throws IOException, MavenExecutionException {
    return POMFile.makeFromParent(file.getParentFile(), text);
  }

  private static ThreadLocal<Boolean> resolving = new ThreadLocal<Boolean>() {
    @Override
    protected Boolean initialValue() {
      return Boolean.FALSE;
    }
  };

  private final File file;
  private final String text;
  private String newText;
  private volatile boolean parentPOMFileInited = false;
  private POMFile parentPOMFile;

  private final POMFile groupDeclarator;
  private final POMFile versionDeclarator;

  private POMFile(final File file, final String text, final String groupId, final String artifactId, final String version) throws IOException, MavenExecutionException {
    super(groupId, artifactId, version);
    this.file = file;
    this.text = text;
    this.newText = text;

    groupDeclarator = groupId != null ? this : getParent() != null ? getParent().groupDeclarator : null;
    versionDeclarator = version != null ? this : getParent() != null ? getParent().versionDeclarator : null;

    if (!resolving.get()) {
      resolving.set(true);

      // Drill down to the root parent, and then resolve the dependency tree from there
      POMFile parentPomFile;
      for (parentPomFile = this; parentPomFile.getParent() != null; parentPomFile = parentPomFile.getParent());

      // Resolve all modules of all sub-modules
      POMFile.resolveModules(parentPomFile.getModules());

      // Resolve all relations of all sub-modules
      parentPomFile.resolveRelations();
      POMFile.resolveRelations(parentPomFile.getModules());
    }
  }

  @Override
  public String groupId() {
    return groupDeclarator != this && groupDeclarator != null ? groupDeclarator.groupId() : super.groupId();
  }

  @Override
  public String version() {
    return versionDeclarator != this && versionDeclarator != null ? versionDeclarator.version() : super.version();
  }

  public File file() {
    return file;
  }

  private void setParent(final POMFile parentPOMFile) {
    this.parentPOMFile = parentPOMFile;
    parentPOMFileInited = true;
  }

  public POMFile getParent() throws IOException, MavenExecutionException {
    if (parentPOMFileInited)
      return parentPOMFile;

    parentPOMFileInited = true;
    return parentPOMFile = POMFile.getParent(file, text);
  }

  private POMFile[] modules = null;

  public POMFile[] getModules() throws IOException, MavenExecutionException {
    if (modules != null)
      return modules;

    synchronized (file) {
      if (modules != null)
        return modules;

      final int[][] moduleIndices = VersionUtil.indexOfTag(newText, "project/modules/module", "project/profiles/profile/modules/module");
      if (moduleIndices == null)
        return this.modules = new POMFile[0];

      final POMFile[] modules = new POMFile[moduleIndices.length];
      for (int i = 0; i < moduleIndices.length; i++) {
        final int[] moduleIndex = moduleIndices[i];
        final String moduleName = newText.substring(moduleIndex[0], moduleIndex[1]).trim();
        final File pomFile = new File(file.getParent(), moduleName + "/pom.xml");
        modules[i] = POMFile.parse(pomFile, new String(Files.readAllBytes(pomFile.toPath())));
        modules[i].setParent(this);
      }

      return this.modules = modules;
    }
  }

  private static void resolveModules(final POMFile[] modules) throws IOException, MavenExecutionException {
    for (final POMFile module : modules)
      resolveModules(module.getModules());
  }

  private static void resolveRelations(final POMFile[] modules) throws IOException, MavenExecutionException {
    for (final POMFile module : modules) {
      module.resolveRelations();
      resolveRelations(module.getModules());
    }
  }

  private ManagedModuleId getManagedVersion(final ModuleId moduleId, final boolean dependency) throws IOException, MavenExecutionException {
    if (moduleId.version() != null)
      return new ManagedModuleId(moduleId);

    final POMFile pomFile = (dependency ? managedDependencies : managedPlugins).get(moduleId);
    if (pomFile != null)
      return new ManagedModuleId(pomFile.groupId(), pomFile.artifactId(), pomFile.version(), pomFile, dependency ? DependencyType.MANAGED_DEPENDENCY : DependencyType.MANAGED_PLUGIN);

    if (getParent() == null)
      return null;

    return getParent().getManagedVersion(moduleId, dependency);
  }

  private volatile Boolean relationsResolved = false;
  private final Set<ManagedPOMFile> dependents = new LinkedHashSet<ManagedPOMFile>();
  private final Set<ManagedPOMFile> dependencies = new LinkedHashSet<ManagedPOMFile>();;
  private final Map<ModuleId,POMFile> managedDependencies = new LinkedHashMap<ModuleId,POMFile>();;
  private final Set<ManagedPOMFile> plugins = new LinkedHashSet<ManagedPOMFile>();;
  private final Map<ModuleId,POMFile> managedPlugins = new LinkedHashMap<ModuleId,POMFile>();;
  private final Set<Pair<ManagedPOMFile,DependencyType>> allDependencies = new LinkedHashSet<Pair<ManagedPOMFile,DependencyType>>();;

  public Set<ManagedPOMFile> dependents() {
    return dependents;
  }

  private List<ManagedPOMFile> parseModuleIds(final DependencyType dependencyType) throws IOException, MavenExecutionException {
    final int[][] indices = VersionUtil.indexOfTag(text, dependencyType.scope);
    final List<ManagedPOMFile> moduleIds = new ArrayList<ManagedPOMFile>();
    if (indices != null) {
      for (final int[] index : indices) {
        final ManagedModuleId managedModuleId = getManagedVersion(new ModuleId(text.substring(index[0], index[1]).trim()), dependencyType == DependencyType.DEPENDENCY || dependencyType == DependencyType.MANAGED_DEPENDENCY);
        if (managedModuleId != null) {
          final POMFile pomFile = pomFiles.get(managedModuleId.moduleId());
          if (pomFile != null) {
            moduleIds.add(new ManagedPOMFile(managedModuleId.manager(), dependencyType, pomFile));
            pomFile.dependents.add(new ManagedPOMFile(managedModuleId.manager(), dependencyType, this));
          }
        }
      }
    }

    return moduleIds;
  }

  private void resolveRelations() throws IOException, MavenExecutionException {
    if (relationsResolved)
      return;

    synchronized (relationsResolved) {
      if (relationsResolved)
        return;

      final POMFile parentPOMFile = getParent();
      if (parentPOMFile != null)
        dependents.add(new ManagedPOMFile(parentPOMFile.versionDeclarator, DependencyType.POM, parentPOMFile));

      for (final ManagedPOMFile managedDependency : parseModuleIds(DependencyType.MANAGED_DEPENDENCY)) {
        managedDependencies.put(new ModuleId(managedDependency.pomFile().groupId(), managedDependency.pomFile().artifactId(), null), managedDependency.pomFile());
        allDependencies.add(new Pair<ManagedPOMFile,DependencyType>(managedDependency, DependencyType.MANAGED_DEPENDENCY));
      }

      dependencies.addAll(parseModuleIds(DependencyType.DEPENDENCY));
      for (final ManagedPOMFile dependency : dependencies)
        allDependencies.add(new Pair<ManagedPOMFile,DependencyType>(dependency, DependencyType.DEPENDENCY));

      for (final ManagedPOMFile managedPlugin : parseModuleIds(DependencyType.MANAGED_PLUGIN)) {
        managedPlugins.put(new ModuleId(managedPlugin.pomFile().groupId(), managedPlugin.pomFile().artifactId(), null), managedPlugin.pomFile());
        allDependencies.add(new Pair<ManagedPOMFile,DependencyType>(managedPlugin, DependencyType.MANAGED_PLUGIN));
      }

      plugins.addAll(parseModuleIds(DependencyType.PLUGIN));
      for (final ManagedPOMFile plugin : plugins)
        allDependencies.add(new Pair<ManagedPOMFile,DependencyType>(plugin, DependencyType.PLUGIN));

      relationsResolved = true;
    }
  }

  private final Set<UpdateCommand> updates = new LinkedHashSet<UpdateCommand>();

  public void addUpdate(final UpdateCommand command) {
    updates.add(command);
  }

  public Set<UpdateCommand> updateCommands() {
    return updates;
  }

  public void increaseVersion() throws IOException, MavenExecutionException {
    addUpdate(new UpdateCommand("project", this, VersionUtil.increaseVersion(version())));

    for (final ManagedPOMFile dependent : dependents) {
//      (dependent.manager() != null ? dependent.manager() : dependent.pomFile()).addUpdate(new UpdateCommand(dependent.dependencyType().scope, this, VersionUtil.increaseVersion(version())));
      if (dependent.manager() != null)
        dependent.manager().increaseVersion();

      dependent.pomFile().increaseVersion();
    }
  }

  public void commit(final boolean updateParentPomVersion) throws IOException {
    if (updateParentPomVersion) {
      final int[][] versionIndex = VersionUtil.indexOfTag(newText, "project/parent/version");
      final String version = versionIndex == null ? null : newText.substring(versionIndex[0][0], versionIndex[0][1]).trim();
      newText = newText.substring(0, versionIndex[0][0]) + VersionUtil.increaseVersion(version) + newText.substring(versionIndex[0][1]);
    }

    final int[][] versionIndex = VersionUtil.indexOfTag(newText, "project/version");
    final String version = versionIndex == null ? null : newText.substring(versionIndex[0][0], versionIndex[0][1]).trim();
    newText = newText.substring(0, versionIndex[0][0]) + VersionUtil.increaseVersion(version) + newText.substring(versionIndex[0][1]);

    final RandomAccessFile raf = new RandomAccessFile(file, "rw");
    raf.write(newText.getBytes());
    raf.setLength(raf.getFilePointer());
    raf.close();
  }

  public void rollback() throws IOException {
    if (newText != null) {
      final RandomAccessFile raf = new RandomAccessFile(file, "rw");
      raf.write(text.getBytes());
      raf.setLength(raf.getFilePointer());
      raf.close();
    }
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;

    if (!(obj instanceof POMFile) || !super.equals(obj))
      return false;

    final POMFile that = (POMFile)obj;
    return file != null ? file.equals(that.file) : that.file == null;
  }

  @Override
  public int hashCode() {
    return super.hashCode() * (file != null ? 13 * file.hashCode() : -73);
  }

  @Override
  public String toString() {
    try {
      return super.toString() + "[" + file.getCanonicalFile().getAbsolutePath() + "]";
    }
    catch (final IOException e) {
      return super.toString() + "[" + e.getMessage() + "]";
    }
  }
}