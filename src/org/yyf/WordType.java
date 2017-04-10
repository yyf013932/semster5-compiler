package org.yyf;

/**
 * Created by Daniel on 2016/10/24.
 */

import java.util.HashMap;
import java.util.Map;

/**
 * 单词类型
 */
public enum WordType {
    /**
     * type1
     */
    LPAREN,
    RPAREN,
    LSQUARP,
    RSQUARP,
    LCURLYP,
    RCURLYP,
    SEMI,
    COMMA,
    NEQ,
    /**
     * type2
     */
    PLUS,
    MINUS,
    TIMES,
    DIVIDE,
    PP,
    MM,
    PA,
    MA,
    TA,
    DA,
    GT,
    GTE,
    LT,
    LTE,
    ASSIGN,
    EQ,
    /**
     * type3
     */
    NUM,
    ID,
    COMMENT,
    /**
     * keep words
     */
    K_TYPE,
    K_IF,
    K_ELSE,
    K_RETURN,
    K_WHILE,
    K_EXTRA,
    K_CLASS,
    K_RIGHTS,
    K_SWITCH,
    K_DEFAULT,
    K_BREAK,
    K_CONTINUE,
    K_VOID,
    K_NEW,
    /**
     * error handle
     */
    ERROR,
    TEM,
    KEEPWORDS,
    /**
     * 用来确定单词的具体类型
     */
    END,
    BEGIN,
    TYPE1,
    TYPE2;


    public static Map<WordType, Map<String, WordType>> typeMap = new HashMap<>();


    static {
        Map<String, WordType> type1 = new HashMap<>();
        type1.put("(", LPAREN);
        type1.put(")", RPAREN);
        type1.put("[", LSQUARP);
        type1.put("]", RSQUARP);
        type1.put("{", LCURLYP);
        type1.put("}", RCURLYP);
        type1.put(";", SEMI);
        type1.put(",", COMMA);
        type1.put("!=", NEQ);

        typeMap.put(TYPE1, type1);

        Map<String, WordType> type2 = new HashMap<>();
        type2.put("+", PLUS);
        type2.put("-", MINUS);
        type2.put("*", TIMES);
        type2.put("/", DIVIDE);
        type2.put("++", PP);
        type2.put("--", MM);
        type2.put("+=", PA);
        type2.put("-=", MA);
        type2.put("/=", DA);
        type2.put("*=", TA);
        type2.put(">", GT);
        type2.put(">=", GTE);
        type2.put("<", LT);
        type2.put("<=", LTE);
        type2.put("=", ASSIGN);
        type2.put("==", EQ);

        typeMap.put(TYPE2, type2);
    }


}
