package org.yyf;

/**
 * Created by Daniel on 2016/10/24.
 */

/**
 * 手动配置DFA的状态转换
 * 目前这个版本比较复杂，有考虑用XML文件填写DFA的配置信息再读入
 */
public class DFAConfig {
    private static State startState;
    private static State[] states, errors;

    public static void init() {
        startState = new State();
        int stateSize = 41, errorSize = 3;
        errors = new State[errorSize];
        states = new State[stateSize];
        for (int i = 0; i < stateSize; i++) {
            states[i] = new State();
        }
        for (int i = 0; i < errorSize; i++) {
            errors[i] = new State();
            errors[i].setWordType(WordType.ERROR);
        }

        String dig = "0123456789", hexdig = "0123456789abcdefABCDEF", digExcept0 = "123456789",
                alphabeta = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_$";


        startState.put("123456789", states[2]);
        startState.put("-", states[0]);
        startState.put("+", states[28]);
        startState.put('0', states[1]);
        startState.put("()[]{};,", states[11]);
        startState.put("/", states[12]);
        startState.put(alphabeta, states[20]);
        startState.put(">", states[22]);
        startState.put("<", states[25]);
        startState.put("*", states[35]);
        startState.put("=", states[38]);

        startState.putExcept(errors[0]);

        errors[1].put(alphabeta + dig, errors[1]);

        for (int i = 1; i < 10; i++) {
            states[i].put(alphabeta, errors[1]);
        }


        put(0, new String[]{"-", "=", "0", digExcept0}, new int[]{33, 34, 1, 2});
        states[0].setWordType(WordType.MINUS);
        put(1, new String[]{dig, "xX", "."}, new int[]{2, 4, 3});
        states[1].setWordType(WordType.NUM);
        put(2, new String[]{dig, "."}, new int[]{2, 3});
        put(3, new String[]{dig}, new int[]{5});
        put(4, new String[]{hexdig}, new int[]{9});
        put(5, new String[]{dig, "eE"}, new int[]{5, 6});
        put(6, new String[]{"+-", dig}, new int[]{7, 8});
        put(7, new String[]{dig}, new int[]{8});
        put(8, new String[]{dig}, new int[]{8});
        put(9, new String[]{hexdig}, new int[]{9});
        put(10, new String[]{"="}, new int[]{11});
        states[10].putExcept(errors[2]);

        states[2].setWordType(WordType.NUM);
        states[5].setWordType(WordType.NUM);
        states[8].setWordType(WordType.NUM);
        states[9].setWordType(WordType.NUM);

        states[11].setWordType(WordType.TYPE1);


        put(12, new String[]{"*", "/", "="}, new int[]{13, 16, 18});
        states[12].setWordType(WordType.DIVIDE);

        put(13, new String[]{"*"}, new int[]{14});
        putOther(13, 13);

        put(14, new String[]{"*", "/"}, new int[]{14, 15});
        putOther(14, 13);

        states[15].setWordType(WordType.COMMENT);

        put(16, new String[]{"\n"}, new int[]{17});
        putOther(16, 16);

        states[17].setWordType(WordType.COMMENT);

        states[18].setWordType(WordType.DA);


        put(20, new String[]{alphabeta, dig}, new int[]{20, 20});
        states[20].setWordType(WordType.ID);


        put(22, new String[]{"="}, new int[]{23});
        states[22].setWordType(WordType.GT);

        states[23].setWordType(WordType.GTE);


        put(25, new String[]{"="}, new int[]{26});
        states[25].setWordType(WordType.LT);

        states[26].setWordType(WordType.LTE);


        put(28, new String[]{"+", "=", "0", digExcept0}, new int[]{29, 30, 1, 2});
        states[28].setWordType(WordType.PLUS);

        states[29].setWordType(WordType.PP);

        states[30].setWordType(WordType.PA);


        states[33].setWordType(WordType.MM);

        states[34].setWordType(WordType.MA);

        put(35, new String[]{"="}, new int[]{37});
        states[35].setWordType(WordType.TIMES);

        states[37].setWordType(WordType.TA);

        put(38, new String[]{"="}, new int[]{40});
        states[38].setWordType(WordType.ASSIGN);

        states[40].setWordType(WordType.EQ);
    }

    public static State getStartState() {
        return startState;
    }

    static void put(int index, String[] chars, int[] moveStates) {
        for (int i = 0; i < chars.length; i++) {
            states[index].put(chars[i], states[moveStates[i]]);
        }
    }

    static void putOther(int index, int index1) {
        states[index].putExcept(states[index1]);
    }

}
