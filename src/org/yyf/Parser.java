package org.yyf;

import com.sun.javafx.binding.StringFormatter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static org.yyf.WordType.*;

/**
 * Created by Daniel on 2016/11/5.
 */
public class Parser {
    /**
     * 产生式列表
     */
    List<Production> productions;
    /**
     * 预测分析表
     */
    Map<String, Map<WordType, Production>> parseTable;
    /**
     * 需要用到的词法分析器
     */
    Lexer lexer;
    /**
     * Token序列
     */
    List<Token> tokens;
    /**
     * Token指针
     */
    int index;
    /**
     * 符号栈
     */
    Stack<Symbol> symbolStack;
    /**
     * 推导的产生式序列
     */
    List<Production> result;

    String outPutFile = "productsSequence.txt";

    void excute() throws Exception {
        while (true) {
            Token token = tokens.get(index);
            if (token.wordType == END && symbolStack.isEmpty()) {
                break;
            }
            Symbol symbol = symbolStack.peek();
            if (symbol.isTerminator) {
                if (symbol.wordType == token.wordType) {
                    symbolStack.pop();
                    index++;
                } else {
                    throw new Exception("error:" + token + "," + symbol);
                }
            } else {
                Production production = parseTable.get(symbol.value).get(token.wordType);
                if (production != null) {
                    symbolStack.pop();
                    result.add(production);
                    push(production);
                } else {
                    throw new Exception("error:" + token + "," + symbol);
                }
            }
        }
    }

    /**
     * 初始化产生式、预测分析表
     */
    void init() {
        initProductions();
        initParseTable();
        readTokenSequence();
        symbolStack = new Stack<>();
        result = new ArrayList<>();
        //开始符压栈
        symbolStack.push(getStartSymbol());
        index = 0;
    }

    /**
     * 获得文法的开始符
     *
     * @return
     */
    Symbol getStartSymbol() {
        return productions.get(0).start;
    }

    /**
     * 产生式压栈
     *
     * @param production
     */
    void push(Production production) {
        for (int j = production.productSequence.size() - 1; j >= 0; j--) {
            symbolStack.push(production.productSequence.get(j));
        }
    }

    /**
     * 初始化产生式
     */
    void initProductions() {
        productions = new ArrayList<>();
        productions.add(Production.simpleCreateProduction("S", "Stat", "S"));
        productions.add(Production.simpleCreateProduction("S"));
        productions.add(Production.simpleCreateProduction("Stat", "V"));
        productions.add(Production.simpleCreateProduction("Stat", "I"));
        productions.add(Production.simpleCreateProduction("Stat", "W"));
        productions.add(Production.simpleCreateProduction("Stat", "O"));
        productions.add(Production.simpleCreateProduction("V", K_TYPE, ID, "A",
                SEMI));
        productions.add(Production.simpleCreateProduction("A"));
        productions.add(Production.simpleCreateProduction("A", ASSIGN, "B"));
        productions.add(Production.simpleCreateProduction("B", ID));
        productions.add(Production.simpleCreateProduction("B", NUM));
        productions.add(Production.simpleCreateProduction("I", K_IF, LPAREN,
                "Con", RPAREN, LCURLYP, "S", RCURLYP, K_ELSE,
                LCURLYP, "S", RCURLYP));
        productions.add(Production.simpleCreateProduction("W", K_WHILE, LPAREN,
                "Con", RPAREN, LCURLYP, "S", RCURLYP));
        productions.add(Production.simpleCreateProduction("Con", "B", "L", "B"));
        productions.add(Production.simpleCreateProduction("L", EQ));
        productions.add(Production.simpleCreateProduction("L", GT));
        productions.add(Production.simpleCreateProduction("O", ID, "Assign", "R", SEMI));
        productions.add(Production.simpleCreateProduction("Assign", ASSIGN));
        productions.add(Production.simpleCreateProduction("Assign", PA));
        productions.add(Production.simpleCreateProduction("R", "P", "R'"));
        productions.add(Production.simpleCreateProduction("R'", PLUS, "P", "R'"));
        productions.add(Production.simpleCreateProduction("R'"));
        productions.add(Production.simpleCreateProduction("P", "Q", "P'"));
        productions.add(Production.simpleCreateProduction("P'", TIMES, "Q", "P'"));
        productions.add(Production.simpleCreateProduction("P'"));
        productions.add(Production.simpleCreateProduction("Q", ID, "T"));
        productions.add(Production.simpleCreateProduction("T"));
        productions.add(Production.simpleCreateProduction("T", PP));
    }

    /**
     * 初始化预测分析表
     */
    void initParseTable() {
        parseTable = new HashMap<>();
        put("S", ID, 0);
        put("S", K_TYPE, 0);
        put("S", K_IF, 0);
        put("S", K_WHILE, 0);
        put("S", END, 1);
        put("S", RCURLYP, 1);
        put("Stat", ID, 5);
        put("Stat", K_TYPE, 2);
        put("Stat", K_IF, 3);
        put("Stat", K_WHILE, 4);
        put("V", K_TYPE, 6);
        put("I", K_IF, 11);
        put("W", K_WHILE, 12);
        put("O", ID, 16);
        put("A", ASSIGN, 8);
        put("A", SEMI, 7);
        put("B", ID, 9);
        put("B", NUM, 10);
        put("Con", ID, 13);
        put("Con", NUM, 13);
        put("L", EQ, 14);
        put("L", GT, 15);
        put("Assign", ASSIGN, 17);
        put("Assign", PA, 18);
        put("R", ID, 19);
        put("R'", PLUS, 20);
        put("R'", SEMI, 21);
        put("P", ID, 22);
        put("P'", TIMES, 23);
        put("P'", PLUS, 24);
        put("P'", SEMI, 24);
        put("P'", ID, 24);
        put("Q", ID, 25);
        put("T", PLUS, 26);
        put("T", TIMES, 26);
        put("T", PP, 27);
        put("T", SEMI, 26);
        put("T", ID, 26);

    }

    /**
     * 分析标记序列
     */
    void readTokenSequence() {
        lexer = new Lexer();
        lexer.readFile();
        lexer.execute();
        lexer.writeFile();
        tokens = lexer.getTokenSequence();
    }

    void writeFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(outPutFile));
            int i = 1;
            for (Production p : result) {
                writer.write(StringFormatter.format("%-2d", i++).getValueSafe() + ":" + p.toString
                        () + "\n");
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 辅助的put函数
     *
     * @param symbol
     * @param type
     * @param i
     */
    private void put(String symbol, WordType type, int i) {
        if (!parseTable.containsKey(symbol))
            parseTable.put(symbol, new HashMap<>());
        parseTable.get(symbol).put(type, productions.get(i));
    }

    public static void main(String[] args) throws Exception {
        Parser parser = new Parser();
        parser.init();
        parser.excute();
        parser.writeFile();

    }
}
