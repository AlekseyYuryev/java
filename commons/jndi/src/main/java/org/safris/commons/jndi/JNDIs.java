package org.safris.commons.jndi;

import java.util.StringTokenizer;
import javax.naming.Context;
import javax.naming.NamingException;
import tyrex.tm.RuntimeContext;

public final class JNDIs {
    /**
     * Binds a name to an object.
     * All intermediate contexts and the target context (that named by all
     * but terminal atomic component of the name) must already exist.
     *
     * @param name
     *      the name to bind; may not be empty
     * @param obj
     *      the object to bind; possibly null
     * @throws  NameAlreadyBoundException if name is already bound
     * @throws  javax.naming.directory.InvalidAttributesException
     *      if object did not supply all mandatory attributes
     * @throws  NamingException if a naming exception is encountered
     *
     * @see #bind(String, Object)
     * @see #rebind(Name, Object)
     * @see javax.naming.directory.DirContext#bind(Name, Object,
     *      javax.naming.directory.Attributes)
     */
    public static void bind(String name, Object obj) throws NamingException {
        if (name == null)
            throw new NullPointerException("name == null");

        if (!name.startsWith("java:"))
            throw new NamingException("!name.startsWith(\"java:\")");

        final RuntimeContext runtimeContext = RuntimeContext.newRuntimeContext();
        Context context = runtimeContext.getEnvContext();

        final StringTokenizer tokenizer = new StringTokenizer(name.substring(5), "/");
        while (tokenizer.hasMoreTokens()) {
            final String token = tokenizer.nextToken();
            if (tokenizer.hasMoreTokens())
                context = context.createSubcontext(token);
            else
                context.bind(token, obj);
        }

        RuntimeContext.setRuntimeContext(runtimeContext);
    }

    private JNDIs() {
    }
}
