package org.yyf;

/**
 * Created by Daniel on 2016/11/5.
 */

import java.util.ArrayList;
import java.util.List;

/**
 * 产生式
 */
public class Production {
    /**
     * 开始符，不可为空
     */
    Symbol start;

    /**
     * 推导序列，可以为空，代表推出空串
     * 文法符号必须有序的排列
     */
    List<Symbol> productSequence;

    public Production(Symbol start, Symbol... ses) {
        this.start = start;
        productSequence = new ArrayList<>();
        for (Symbol symbol : ses) {
            productSequence.add(symbol);
        }
    }

    public Production() {
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(start.toString() + " → ");
        if (productSequence.isEmpty()) {
            sb.append("ε");
        } else {
            for (Symbol s : productSequence) {
                sb.append(s.toString() + " ");
            }
        }
        return sb.toString();
    }

    public static Production simpleCreateProduction(Object... objs) {
        if (objs.length < 1 || objs[0].getClass() != String.class)
            return null;
        Production production = new Production();
        production.start = new Symbol((String) objs[0]);
        production.productSequence = new ArrayList<>();
        for (int i = 1; i < objs.length; i++) {
            Class type = objs[i].getClass();
            if (type == String.class) {
                production.productSequence.add(new Symbol((String) objs[i]));
            } else {
                production.productSequence.add(new Symbol((WordType) objs[i]));
            }
        }
        return production;
    }



}
