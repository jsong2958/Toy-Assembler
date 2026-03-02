package io.github.jsong2958.toyassembler.parser;

public class Token {
    private String label;
    private String op;
    private String arg1;
    private String arg2;
    private String comment;
    private boolean isLabel;
    private boolean isComment;
    private Integer lineNum;

    public int getArgCount() {
        int count = 0;
        if (this.arg1 != null) count++;
        if (this.arg2 != null) count++;

        return count;
    }

    public String label() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String op() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String arg1() {
        return arg1;
    }

    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }

    public String arg2() {
        return arg2;
    }

    public void setArg2(String arg2) {
        this.arg2 = arg2;
    }


    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isLabel() {
        return this.isLabel;
    }

    public void setIsLabel(boolean isLabel) {
        this.isLabel = isLabel;
    }

    public boolean isComment() {
        return this.isComment;
    }

    public void setIsComment(boolean isComment) {
        this.isComment = isComment;
    }

    public Integer lineNum() {
        return this.lineNum;
    }

    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }


    @Override
    public String toString() {
        return "Token{" +
                "label='" + label + '\'' +
                ", op='" + op + '\'' +
                ", arg1='" + arg1 + '\'' +
                ", arg2='" + arg2 + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }

    public String argsToString() {
        StringBuilder string = new StringBuilder();
        if (arg1 != null && arg2 == null) string.append(arg1);
        if (arg1 != null && arg2 != null) string.append(arg1 + ", " + arg2);
        if (arg1 == null && arg2 != null) string.append(arg2);
        return string.toString();
    }

}
