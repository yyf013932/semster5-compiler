package org.yyf;

/**
 * Created by Daniel on 2016/10/24.
 */

/**
 * 输出标记结构
 * 含有行号、类型、值
 */
public class Token {
    /**
     * 序号
     */
    int id;
    /**
     * 行号
     */
    int lineNumber;
    /**
     * 列号
     */
    int columnNumber;
    /**
     * 单词类型
     */
    WordType wordType;
    /**
     * 值
     */
    String value;

    public static Token getEndToken() {
        Token endToken = new Token();
        endToken.wordType = WordType.END;
        endToken.value = "$r";
        return endToken;
    }


    public String toString() {
        return id + "," + lineNumber + ":" + columnNumber + "," + wordType + "\n" + value + "";
    }
}
