package org.yyf;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Daniel on 2016/10/24.
 */
public class State {
    /**
     * 转换表
     */
    private Map<Character, State> stateMap;
    /**
     * 获得结果类型，
     */
    private WordType wordType;
    /**
     * 跳转向map中不存在的键的状态，在DFA中通常是[except]边连接的状态，通常是error处理，可以为NULL
     */
    private State otherState;

    public State(Map<Character, State> map, WordType wordType) {
        stateMap = map;
        this.wordType = wordType;
    }

    public State() {
        this(WordType.TEM);
    }

    public State(WordType type) {
        stateMap = new HashMap<>();
        wordType = type;
    }

    /**
     * 添加转换状态
     *
     * @param ch
     * @param state
     */
    public void put(char ch, State state) {
        stateMap.put(ch, state);
    }

    public void put(String chars, State state) {
        for (char ch : chars.toCharArray()) {
            stateMap.put(ch, state);
        }
    }

    /**
     * 设置其他状态
     * @param state
     */
    public void putExcept(State state) {
        this.otherState = state;
    }

    /**
     * 设置类型
     *
     * @param type
     */
    public void setWordType(WordType type) {
        this.wordType = type;
    }

    /**
     * 状态是否可以接收对应字符
     *
     * @param c 待测试的字符
     * @return
     */
    public boolean isAcceptable(char c) {
        return otherState != null || stateMap != null && stateMap.get(c) != null;
    }

    /**
     * 接收对应字符，返回下一状态
     *
     * @param c 待接收的字符
     * @return
     */
    public State accept(char c) {
        if (stateMap != null && stateMap.get(c) != null)
            return stateMap.get(c);
        return otherState;
    }

    /**
     * 获得当前状态的类型
     *
     * @return
     */
    public WordType getType() {
        return wordType;
    }

}
