package org.yyf;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Lexer {
    ArrayList<String> lineDivide;
    ArrayList<Token> tokenSequences;
    String inputFile = "input.txt", outPutFile = "token.txt";
    int currentLineNum, currentColNum;

    public void execute() {
        DFA dfa = new DFA();
        int id = 1;
        char c;
        while ((c = getNextChar()) != 0) {
            if (dfa.isAcceptable(c)) {
                dfa.accept(c);
                move();
            } else {
                Token token = new Token();
                token.lineNumber = currentLineNum + 1;
                token.wordType = dfa.getReturnType();
                token.value = dfa.getContent();
                token.columnNumber = currentColNum + 1;
                token.id = id++;
                tokenSequences.add(token);
                dfa.reSetState();
            }
        }
        Token endToken = Token.getEndToken();
        endToken.id = id;
        endToken.lineNumber = currentLineNum;
        endToken.columnNumber = currentColNum;
        tokenSequences.add(endToken);
    }

    /**
     * 读文件
     */
    public void readFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            String tem;
            while ((tem = reader.readLine()) != null) {
                lineDivide.add(tem + "\n");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写文件
     */
    void writeFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(outPutFile));
            for (Token token : tokenSequences) {
                writer.write(token.toString() + "\n");
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Lexer() {
        lineDivide = new ArrayList<>();
        tokenSequences = new ArrayList<>();
        currentColNum = -1;
        currentLineNum = 0;
    }

    public List<Token> getTokenSequence() {
        return tokenSequences;
    }

    /**
     * 获得下一个字符，但是不移动指针
     *
     * @return
     */
    public char getNextChar() {
        if (currentColNum < lineDivide.get(currentLineNum).length() - 1) {
            return lineDivide.get(currentLineNum).charAt(currentColNum + 1);
        } else if (currentLineNum < lineDivide.size() - 1) {
            return lineDivide.get(currentLineNum + 1).charAt(0);
        } else {
            return 0;
        }
    }

    /**
     * 移动指针
     */
    public void move() {
        if (currentColNum < lineDivide.get(currentLineNum).length() - 1) {
            currentColNum++;
        } else if (currentLineNum < lineDivide.size() - 1) {
            currentLineNum++;
            currentColNum = 0;
        }
    }

    public static void main(String[] args) {
        Lexer lexer = new Lexer();
        lexer.readFile();
        lexer.execute();
        lexer.writeFile();
    }

}
