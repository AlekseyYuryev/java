package org.safris.cdm.lexer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.safris.commons.util.StreamSearcher;

public class Lexer {
  public static void main(final String[] args) throws Exception {
    new Lexer(new File("/Users/seva/Work/safris/org/commons/dbcp/target/generated-sources/xmlbinding/org/safris/xml/schema/binding/dbcp/$dbcp_dbcpType.java"));
  }

  private static final StreamSearcher eol = new StreamSearcher(new byte[] {'\r'}, new byte[] {'\n'});
  private static final StreamSearcher closeComment = new StreamSearcher(new byte[] {'*', '/'});
  private static final StreamSearcher singleQuote = new StreamSearcher(new byte[] {'\''});
  private static final StreamSearcher doubleQuote = new StreamSearcher(new byte[] {'"'});

  private static class Token {
    public final int start;
    public final int length;
    public final Op op;

    public Token(final int start, final int length, final Op op) {
      this.start = start;
      this.length = length;
      this.op = op;
    }
  }
  
  public static int binarySearch(final Op[] sorted, final char key, final int from, final int to) {
    int low = from;
    int high = to - 1;

    while (low <= high) {
      int mid = (low + high) >>> 1;
      char midVal = sorted[mid].lcname().charAt(0);

      if (midVal < key)
        low = mid + 1;
      else if (midVal > key)
        high = mid - 1;
      else
        return mid; // key found
    }
    
    return -(low + 1);  // key not found.
  }
  
  public enum Op {
    DIV, LCOM, BCOM, SQUOTE, DQUOTE, DOT, COLON, SCOLON, STAR, OPAR, CPAR, OBRKT, CBRKT, OSQUIG, CSQUIG, COMMA, NUMBER, ARRAY, PL, PLPL, PLEQ, MINUS, MINUSMINUS, MINUSEQ, GT, LT, GTGT, GTGTGT, LTLT, LTLTLT, GTE, LTE, EQ, EQEQ, CARRET, MOD, NOT, TILDE, AND, ANDAND, OR, OROR, AT, QUEST, WORD,
    ABSTRACT(0, 1, 2), ASSERT(1, 0, 2), BOOLEAN(0, 2, 2), BREAK(1, 1, 2), BYTE(2, 0, 2), CASE(0, 5, 3), CATCH(1, 4, 3), CHAR(2, 3, 2), CLASS(3, 2, 2), CONST(4, 1, 2), CONTINUE(5, 0, 4), DEFAULT(0, 2, 1), DO(1, 1, 3), DOUBLE(2, 0, 3), ELSE(0, 2, 2), ENUM(1, 1, 2), EXTENDS(2, 0, 2), FALSE(0, 4, 2), FINAL(1, 3, 6), FINALLY(2, 2, 6), FLOAT(3, 1, 2), FOR(4, 0, 2), GOTO(0, 0, 1), IF(0, 5, 2), IMPLEMENTS(1, 4, 4), IMPORT(2, 3, 4), INSTANCEOF(3, 2, 3), INT(4, 1, 4), INTERFACE(5, 0, 4), LONG(0, 0, 1), NATIVE(0, 2, 2), NEW(1, 1, 2), NULL(2, 0, 2), PACKAGE(0, 3, 2), PRIVATE(1, 2, 3), PROTECTED(2, 1, 3), PUBLIC(3, 0, 2), RETURN(0, 0, 1), SHORT(0, 5, 2), STATIC(1, 4, 3), STRICTFP(2, 3, 3), SUPER(3, 2, 2), SWITCH(4, 1, 2), SYNCHRONIZED(5, 0, 2), THIS(0, 5, 3), THROW(1, 4, 6), THROWS(2, 3, 6), TRANSIENT(3, 2, 3), TRUE(4, 1, 3), TRY(5, 0, 3), VOID(0, 1, 3), VOLATILE(1, 0, 3), WHILE(0, 0, 1);
    
    private final String name;
    private final int bck;
    private final int fwd;
    private final int threshold;
    
    Op(final int bck, final int fwd, final int threshold) {
      this.name = name().toLowerCase();
      this.bck = bck;
      this.fwd = fwd;
      this.threshold = threshold;
    }
    
    Op() {
      this(-1, -1, -1);
    }
    
    public String lcname() {
      return name;
    }
  }

  private static void addToken(final List<Token> tokens, final int start, final int length, final Op op) {
    if (0 < length && op != null)
      tokens.add(new Token(start - 1, length - 1, op));
  }

  public Lexer(final File file) throws IOException {
    final int length = (int)file.length();
    final byte[] bytes = new byte[length];
    final FileInputStream in = new FileInputStream(file);
    final List<Token> tokens = new ArrayList<Token>();
    int i = 0;
    char c;
    Op op = null;
    int len = 0;
    while (i < bytes.length) {
      in.read(bytes, i, 1);
      c = (char)bytes[i++];
      if ('0' <= c && c <= '9' && (op == null || op.ordinal() < Op.WORD.ordinal())) {
        if (op != Op.NUMBER) {
          addToken(tokens, i - len, len, op);
          len = 1;
          op = Op.NUMBER;
        }
        else {
          ++len;
        }
      }
      else if (('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z') || (len != 0 && '0' <= c && c <= '9') || c == '$' || c == '_') {
        if (op == null || op.ordinal() < Op.WORD.ordinal()) {
          addToken(tokens, i - len, len, op);
          len = 1;

          final int found = binarySearch(Op.values(), c, Op.ABSTRACT.ordinal(), Op.WHILE.ordinal() + 1);
          op = found <= Op.WORD.ordinal() ? Op.WORD : Op.values()[found];
        }
        else if (op == Op.WORD) {
          ++len;
        }
        else {
          boolean found = op.threshold <= len && (op.lcname().length() <= len || op.lcname().charAt(len) == c);
          if (!found) {
            // FIXME: The algorithm to detect keywords can be made even more efficient: upon runtime declaration of each enum,
            // FIXME: the enum name should be checked against other names for similarity of chars. For each char, the indices
            // FIXME: of all other possible matches should be provided, in sorted order to be able to use binary search.
            for (int k = op.ordinal() - op.bck; k <= op.ordinal() + op.fwd; k++) {
              final String name = Op.values()[k].lcname();
              if (name.length() <= len)
                continue;
              
              final char kc = name.charAt(len);
              if (kc == c) {
                op = Op.values()[k];
                found = true;
                break;
              }
            }
          }
          
          if (!found)
            op = Op.WORD;
          
          ++len;
        }
      }
      else if (c == '\n' || c == '\r' || c == ' ' || c == '\t') {
        if (op != null) {
          addToken(tokens, i - len, len, op);
          len = 0;
          op = null;
        }
      }
      else if (c == '.') {
        if (op == null || op == Op.OBRKT || op == Op.OSQUIG) {
          len = 1;
          op = Op.NUMBER;
        }
        else if (op == Op.NUMBER) {
          ++len;
        }
        else {
          addToken(tokens, i - len, len, op);
          len = 1;
          op = Op.DOT;
        }
      }
      else if (c == '&') {
        if (op == Op.AND) { // &&
          len = 2;
          op = Op.ANDAND;
        }
        else {
          addToken(tokens, i - len, len, op);
          len = 1;
          op = Op.AND;
        }
      }
      else if (c == '|') {
        if (op == Op.OR) { // ||
          len = 2;
          op = Op.OROR;
        }
        else {
          addToken(tokens, i - len, len, op);
          len = 1;
          op = Op.OR;
        }
      }
      else if (c == '=') {
        if (op == Op.LT) { // <=
          len = 2;
          op = Op.LTE;
        }
        else if (op == Op.GT) { // >=
          len = 2;
          op = Op.GTE;
        }
        else if (op == Op.EQ) { // ==
          len = 2;
          op = Op.EQEQ;
        }
        else if (op == Op.MINUS) { // -=
          len = 2;
          op = Op.MINUSEQ;
        }
        else if (op == Op.PL) { // +=
          len = 2;
          op = Op.PLEQ;
        }
        else {
          addToken(tokens, i - len, len, op);
          len = 1;
          op = Op.EQ;
        }
      }
      else if (c == '<') {
        if (op == Op.LT) { // <<
          len = 2;
          op = Op.LTLT;
        }
        else if (op == Op.LTLT) { // <<<
          len = 3;
          op = Op.LTLTLT;
        }
        else {
          addToken(tokens, i - len, len, op);
          len = 1;
          op = Op.LT;
        }
      }
      else if (c == '>') {
        if (op == Op.GT) { // >>
          len = 2;
          op = Op.GTGT;
        }
        else if (op == Op.GTGT) { // >>>
          len = 3;
          op = Op.GTGTGT;
        }
        else {
          addToken(tokens, i - len, len, op);
          len = 1;
          op = Op.GT;
        }
      }
      else if (c == '-') {
        if (op == Op.MINUS) { // --
          len = 2;
          op = Op.MINUSMINUS;
        }
        else {
          addToken(tokens, i - len, len, op);
          len = 1;
          op = Op.MINUS;
        }
      }
      else if (c == '+') {
        if (op == Op.PL) { // ++
          len = 2;
          op = Op.PLPL;
        }
        else {
          addToken(tokens, i - len, len, op);
          len = 1;
          op = Op.PL;
        }
      }
      else if (c == '~') {
        addToken(tokens, i - len, len, op);
        len = 1;
        op = Op.TILDE;
      }
      else if (c == '!') {
        addToken(tokens, i - len, len, op);
        len = 1;
        op = Op.NOT;
      }
      else if (c == '@') {
        addToken(tokens, i - len, len, op);
        len = 1;
        op = Op.AT;
      }
      else if (c == '^') {
        addToken(tokens, i - len, len, op);
        len = 1;
        op = Op.CARRET;
      }
      else if (c == '%') {
        addToken(tokens, i - len, len, op);
        len = 1;
        op = Op.MOD;
      }
      else if (c == ',') {
        addToken(tokens, i - len, len, op);
        len = 1;
        op = Op.COMMA;
      }
      else if (c == ';') {
        addToken(tokens, i - len, len, op);
        len = 1;
        op = Op.SCOLON;
      }
      else if (c == ':') {
        addToken(tokens, i - len, len, op);
        len = 1;
        op = Op.COLON;
      }
      else if (c == '(') {
        addToken(tokens, i - len, len, op);
        len = 1;
        op = Op.OPAR;
      }
      else if (c == ')') {
        addToken(tokens, i - len, len, op);
        len = 1;
        op = Op.CPAR;
      }
      else if (c == '[') {
        addToken(tokens, i - len, len, op);
        len = 1;
        op = Op.OBRKT;
      }
      else if (c == '{') {
        addToken(tokens, i - len, len, op);
        len = 1;
        op = Op.OSQUIG;
      }
      else if (c == '}') {
        addToken(tokens, i - len, len, op);
        len = 1;
        op = Op.CSQUIG;
      }
      else if (c == '?') {
        addToken(tokens, i - len, len, op);
        len = 1;
        op = Op.QUEST;
      }
      else if (c == ']') {
        if (op == Op.OBRKT) { // []
          len = 2;
          op = Op.ARRAY;
        }
        else {
          addToken(tokens, i - len, len, op);
          len = 1;
          op = Op.CBRKT;
        }
      }
      else if (c == '/') {
        if (op == Op.DIV) { // Start of line comment
          // find end of line
          // index from // to end of comment, not including newline
          // this is the only situation where the token is added at time of detection of end of block, cause the eol char is not supposed to be a part of the token
          len = eol.search(in, bytes, i);
          addToken(tokens, i, len + 1, Op.LCOM);
          i += len;
          len = 0;
          op = null;
        }
        else {
          addToken(tokens, i - len, len, op);
          len = 1;
          op = Op.DIV;
        }
      }
      else if (c == '*') {
        if (op == Op.DIV) { // Start of block comment
          // find end of block comment
          // index from /* to */ including any & all characters between
          i += len = closeComment.search(in, bytes, i);
          len += 2;
          op = Op.BCOM;
        }
        else {
          addToken(tokens, i - len, len, op);
          len = 1;
          op = Op.STAR;
        }
      }
      else if (c == '\'') {
        addToken(tokens, i - len, len, op);
        len = 1;
        int t;
        i += t = singleQuote.search(in, bytes, i);
        len += t;
        // take care of '\'' situation
        if (bytes[i - 2] == '\\' && len == 3) {
          i += t = singleQuote.search(in, bytes, i);
          len += t;
        }

        op = Op.SQUOTE;
      }
      else if (c == '"') {
        addToken(tokens, i - len, len, op);
        len = 1;
        int l;
        i += l = doubleQuote.search(in, bytes, i);
        len += l;
        // take care of "\"" situation
        if (bytes[i - 2] == '\\' && len == 3) {
          i += l = doubleQuote.search(in, bytes, i);
          len += l;
        }

        op = Op.DQUOTE;
      }
      else {
        System.err.print(c);
      }
    }
    
    // add the last token, because it's final delimiter can be the EOF
    addToken(tokens, i - len + 1, len, op);

    for (int x = 0; x < tokens.size(); x++) {
      final Token token = tokens.get(x);
      System.out.println(token.op + ":\t" + new String(bytes, token.start, token.length + 1));
    }
  }
}