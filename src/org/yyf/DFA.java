package org.yyf;

/**
 * Created by Daniel on 2016/10/24.
 */

import java.util.HashMap;
import java.util.Map;

/**
 * 有限状态机
 */
public class DFA {
    /**
     * 开始状态
     */
    private State startState;
    /**
     * 当前状态
     */
    private State currentState;
    /**
     * 当前处理的内容
     */
    private StringBuilder content;
    /**
     * 用一个哈希表保存关键字信息
     * 用哈希表的原因是加快查找速度
     */
    private Map<String, WordType> keepWords;
    /**
     * 具体的保留字，需要put到keepWords里
     */
    private String[] words = new String[]{"int", "double",
            "if", "else", "return", "while", "const", "static", "class",
            "public", "private", "protected", "switch", "default", "break", "continue", "void",
            "new"};

    public DFA() {
        if (DFAConfig.getStartState() == null)
            DFAConfig.init();
        keepWords = new HashMap<>();

        keepWords.put("int", WordType.K_TYPE);
        keepWords.put("double", WordType.K_TYPE);
        keepWords.put("if", WordType.K_IF);
        keepWords.put("else", WordType.K_ELSE);
        keepWords.put("return", WordType.K_RETURN);
        keepWords.put("while", WordType.K_WHILE);
        keepWords.put("const", WordType.K_EXTRA);
        keepWords.put("static", WordType.K_EXTRA);
        keepWords.put("class", WordType.K_CLASS);
        keepWords.put("public", WordType.K_RIGHTS);
        keepWords.put("private", WordType.K_RIGHTS);
        keepWords.put("protected", WordType.K_RIGHTS);
        keepWords.put("switch",WordType.K_SWITCH);
        keepWords.put("default",WordType.K_DEFAULT);
        keepWords.put("break",WordType.K_BREAK);
        keepWords.put("continue",WordType.K_CONTINUE);
        keepWords.put("void",WordType.K_VOID);
        keepWords.put("new",WordType.K_NEW);

        startState = DFAConfig.getStartState();
        reSetState();
    }

    /**
     * 获得处理内容
     *
     * @return
     */
    public String getContent() {
        return content.toString();
    }

    /**
     * 获得返回的单词类型
     *
     * @return
     */
    public WordType getReturnType() {
        WordType type = currentState.getType();
        String content=getContent();
        switch (type) {
            case TYPE1:
                return WordType.typeMap.get(type).get(content);
        }
        if (keepWords.get(content) != null) {
            return keepWords.get(content);
        }
        return type;
    }

    /**
     * 重置DFA处理状态
     */
    public void reSetState() {
        content = new StringBuilder();
        currentState = startState;
    }


    /**
     * 接收字符
     *
     * @param c
     */
    public void accept(char c) {
        if (startState == currentState && (c == ' ' || c == '\t' || c == '\n'))
            return;
        if (!(c == ' ' || c == '\t' || c == '\n'))
            content.append(c);
        currentState = currentState.accept(c);
    }

    /**
     * 测试是否可以接收字符
     *
     * @param c
     * @return
     */
    public boolean isAcceptable(char c) {
        return currentState.isAcceptable(c);
    }

}
