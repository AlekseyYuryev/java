package org.safris.commons.net.mail;

public class MimeContent {
  private final String content;
  private final String type;

  public MimeContent(final String content, final String type) {
    this.content = content;
    this.type = type;
  }

  public String getContent() {
    return content;
  }

  public String getType() {
    return type;
  }
}