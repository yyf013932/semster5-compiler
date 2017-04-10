package org.yyf;

/**
 * Created by Daniel on 2016/11/5.
 */

/**
 * 文法符号
 * 可以为终结符，也可以为非终结符
 * 终结符wordType不能为空，代表具体的单词类型
 * 非终结符value不能为空，代表非终结符名称
 */
public class Symbol {
    /**
     * 是否为终结符
     */
    boolean isTerminator;
    /**
     * 为非终结符时非终结符的名字
     */
    String value;
    /**
     * 为终结符时终结符的单词类型
     */
    WordType wordType;

    @Override
    public String toString() {
        if (isTerminator)
            return "$"+wordType.toString();
        else
            return value;
    }

    /**
     * 构建非终结符
     * @param value
     */
    public Symbol(String value) {
        isTerminator = false;
        this.value = value;
    }

    /**
     * 构建终结符
     * @param wordType
     */
    public Symbol(WordType wordType) {
        isTerminator = true;
        this.wordType = wordType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Symbol symbol = (Symbol) o;
        if (isTerminator != symbol.isTerminator) return false;
        if (isTerminator) {
            return wordType != null && symbol.wordType != null && wordType.equals(symbol.wordType);
        } else {
            return value != null && symbol.value != null && value.equals(symbol.value);
        }

    }

    @Override
    public int hashCode() {
        int result = (isTerminator ? 1 : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (wordType != null ? wordType.hashCode() : 0);
        return result;
    }
}
