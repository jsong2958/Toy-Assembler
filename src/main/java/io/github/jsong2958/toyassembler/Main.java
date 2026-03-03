package io.github.jsong2958.toyassembler;

import io.github.jsong2958.toyassembler.assembler.Assembler;
import io.github.jsong2958.toyassembler.error.SyntaxError;
import io.github.jsong2958.toyassembler.isa.Opcode;
import io.github.jsong2958.toyassembler.isa.Register;
import io.github.jsong2958.toyassembler.parser.Parser;
import io.github.jsong2958.toyassembler.parser.Token;
import io.github.jsong2958.toyassembler.symbol.SymbolTable;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1 || !(args[0].split("\\.")[1].equals("tsm"))) {
            System.out.println("Usage: java -jar toyassembler.jar [path/to/file.tsm]");
            return;
        }


        File file = new File(args[0]);
        
        BufferedReader br;

        //-----------First Pass------------------

        List<Token> tokens = new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader(file));

            Parser parser = new Parser();

            SymbolTable table = new SymbolTable();

            int lineNum = 1;
            String line = br.readLine();

            int pc = 0;
            while (line != null) {
                Token t = parser.parse(line, lineNum++);
                line = br.readLine();
                if (t == null) {
                    continue;
                }

                if (t.isComment()) continue;

                tokens.add(t);

                if (t.isLabel()) {
                    table.mapLabel(t, pc);
                    continue;
                }

                if (t.label() != null) table.mapLabel(t, pc);

                try {

                    String op = t.op().toUpperCase();
                    Opcode opcode = Opcode.valueOf(op);

                    if (t.getArgCount() != opcode.getArgCount()) {
                        throw new SyntaxError("SyntaxError at line " + t.lineNum() + ": " +
                                t.op() + " expected " + opcode.getArgCount() + " arguments, got " + t.getArgCount() + " instead");
                    }


                    switch (opcode) {
                        case Opcode.MOV -> {
                            String arg1 = t.arg1().toUpperCase();
                            String arg2 = t.arg2().toUpperCase();

                            if (isRegister(arg1) && isRegister(arg2)) {
                                t.setOp("MOV_REG_REG");
                            } else if (isLiteral(arg1) && isRegister(arg2)) {
                                t.setOp("MOV_IMM_REG");
                            } else {
                                throw new SyntaxError("SyntaxError at line " + t.lineNum() + ": Invalid instruction\n" + t.op());
                            }
                        }

                        case Opcode.ADD -> {
                            String arg1 = t.arg1().toUpperCase();
                            String arg2 = t.arg2().toUpperCase();

                            if (isRegister(arg1) && isRegister(arg2)) {
                                t.setOp("ADD_REG_REG");
                            } else if (isLiteral(arg1) && isRegister(arg2)) {
                                t.setOp("ADD_IMM_REG");
                            } else {
                                throw new SyntaxError("SyntaxError at line " + t.lineNum() + ": Invalid instruction\n" + t.op());
                            }
                        }

                        case Opcode.AND ->  {
                            String arg1 = t.arg1().toUpperCase();
                            String arg2 = t.arg2().toUpperCase();

                            if (isRegister(arg1) && isRegister(arg2)) {
                                t.setOp("AND_REG_REG");
                            } else if (isLiteral(arg1) && isRegister(arg2)) {
                                t.setOp("AND_IMM_REG");
                            } else {
                                throw new SyntaxError("SyntaxError at line " + t.lineNum() + ": Invalid instruction\n" + t.op());
                            }
                        }

                        case Opcode.SUB -> {
                            String arg1 = t.arg1().toUpperCase();
                            String arg2 = t.arg2().toUpperCase();

                            if (isRegister(arg1) && isRegister(arg2)) {
                                t.setOp("SUB_REG_REG");
                            } else if (isLiteral(arg1) && isRegister(arg2)) {
                                t.setOp("SUB_IMM_REG");
                            } else {
                                throw new SyntaxError("SyntaxError at line " + t.lineNum() + ": Invalid instruction\n" + t.op());
                            }
                        }
                    }

                    opcode = Opcode.valueOf(t.op().toUpperCase());
                    pc += opcode.width();

                } catch (IllegalArgumentException e) {
                    throw new SyntaxError("SyntaxError at line " + t.lineNum() + ": Invalid instruction\n" + t.op());
                }

            }

            //--------------Second pass---------------

            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            Assembler assembler = new Assembler();

            for (Token t : tokens) {

                if (t.isLabel() || t.isComment()) continue;

                byte[] bytes = assembler.translate(t, table);

                if (bytes == null) throw new SyntaxError("SyntaxError at line " + t.lineNum() + ": Invalid line\n" + t);

                byteStream.write(bytes);
            }

            try (FileOutputStream fileStream = new FileOutputStream(args[0].split("\\.")[0] + ".binary")) {
                byte[] byteArr = byteStream.toByteArray();
                fileStream.write(byteArr);

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }


        } catch (IOException e) {
            System.err.println(e.toString());
        }

    }

    public static boolean isRegister(String arg) {
        try {
            Register register = Register.valueOf(arg);
        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }

    public static boolean isLiteral(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


}
