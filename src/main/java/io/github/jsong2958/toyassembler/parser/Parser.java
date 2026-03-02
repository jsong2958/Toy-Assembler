package io.github.jsong2958.toyassembler.parser;

import io.github.jsong2958.toyassembler.error.SyntaxError;

public class Parser {

    public Token parse(String line, int lineNum) throws SyntaxError {
        if (line.isEmpty()) return null;

        String originalLine = line;

        Token t = new Token();
        t.setLineNum(lineNum);

        line = line.trim();

        int commentIndex = line.indexOf(";");
        if (commentIndex != -1) {
            String comment = line.substring(commentIndex + 1).trim();
            t.setComment(comment);
            line = line.substring(0, commentIndex).trim();
        }

        if (line.isEmpty()) {
            t.setIsComment(true);
            return t;
        }

        if (line.endsWith(":")) {
            String label = line.substring(0, line.length() - 1).trim();

            if (label.isEmpty()) throw new SyntaxError("SyntaxError at line " + lineNum + ": Empty label");

            t.setLabel(label);
            t.setIsLabel(true);
            return t;
        }


        String[] split1 = line.split(":");


        if (split1.length > 2) throw new SyntaxError("SyntaxError at line " + lineNum + ": More than one label in the same line\n" + originalLine);
        String label = (split1.length == 2) ? split1[0].trim() : null;
        t.setLabel(label);

        line = split1[split1.length - 1].trim();

        String[] split2 = line.split(","); //[OP ARG1 ;COMMENT] or [OP ARG1, ARG2 ;COMMENT]
        if (split2.length > 2) throw new SyntaxError("SyntaxError at line " + lineNum + ": More than two arguments in the same line\n" + originalLine);

        String[] split3 = null;

        if (split2.length == 1) { //no second argument

            split3 = split2[0].split(";");

            if (split3.length > 2) throw new SyntaxError("SyntaxError at line " + lineNum + ": More than one comment in one line");

            if (split3.length == 2) { //has comment
                String comment = split3[1].trim();
                t.setComment(comment);
            }

        } else if (split2.length == 2) { //has second argument

            split3 = split2[1].split(";"); //[ARG2] or [ARG2, COMMENT]

            if (split3.length > 2) throw new SyntaxError("SyntaxError at line " + lineNum + ": More than one comment in one line");


            if (split3[0].trim().equals("")) throw new SyntaxError("SyntaxError at line " + lineNum +   ": Missing arg2 after ,\n" + originalLine);

            if (split3.length == 2) {
                String comment = split3[1].trim();
                t.setComment(comment);
            }

            String arg2 = split3[0].trim();
            t.setArg2(arg2);

        }


        line = split2[0].trim();

        if (line.toUpperCase().equals("RET")) {
            t.setOp("RET");
            return t;
        }


        String[] split4 = line.split("\\s+"); //space character
        if (split4.length != 2) throw new SyntaxError("SyntaxError at line " + lineNum + ": Invalid instruction format\n" + originalLine);

        String op = split4[0];
        String arg1 = split4[1];

        t.setOp(op);
        t.setArg1(arg1);


        return t;

    }



}
