package io.github.jsong2958.toyassembler.assembler;

import io.github.jsong2958.toyassembler.error.SyntaxError;
import io.github.jsong2958.toyassembler.isa.Opcode;
import io.github.jsong2958.toyassembler.isa.Register;
import io.github.jsong2958.toyassembler.parser.Token;
import io.github.jsong2958.toyassembler.symbol.SymbolTable;

public class Assembler {

    public byte[] translate(Token t, SymbolTable table) {

        String op = t.op();
        Opcode opcode = Opcode.valueOf(op);

        switch (opcode) {

            case MOV_REG_REG, ADD_REG_REG, AND_REG_REG, SUB_REG_REG, READ, WRITE, CMP, XOR, OR -> {
                return encodeBinary(opcode, t);
            }

            case NEG, NOT, LNOT, CLEAR -> {
                return encodeUnary(opcode, t);
            }

            case JMP, CALL, JE, JNE -> {
                return encodeJump(opcode, t, table);
            }

            case MOV_IMM_REG, ADD_IMM_REG, AND_IMM_REG -> {
                return encodeImm(opcode, t);
            }


            case RET ->  {
                return encodeRet(opcode, t);
            }


        }

        return null;

    }


    private byte[] encodeBinary(Opcode opcode, Token t) {
        String arg1 = t.arg1().toUpperCase();
        String arg2 = t.arg2().toUpperCase();

        Register src = Register.valueOf(arg1);
        Register dest = Register.valueOf(arg2);

        byte out = (byte) (
                opcode.bits() << 4 |
                src.bits() << 2 |
                dest.bits()
        );

        return new byte[] {out};

    }

    private byte[] encodeUnary(Opcode opcode, Token t) {
        byte choice = 0;

        switch (opcode) {
            case NEG -> choice = 0;

            case NOT -> choice = 1;

            case LNOT -> choice = 2;

            case CLEAR -> choice = 3;

        }

        Register dest = Register.valueOf(t.arg1());

        byte code = (byte) (
                opcode.bits() << 4 |
                        choice << 2 |
                        dest.bits()
        );


        return new byte[] {code};

    }

    private byte[] encodeJump(Opcode opcode, Token t, SymbolTable table) {
        String arg1 = t.arg1();
        if (!table.containsKey(arg1)) throw new SyntaxError("SyntaxError at line " + t.lineNum() + ": Cannot find label\n" + arg1);

        int value = table.get(arg1).intValue();

        if (value < -128 || value > 255) {
            throw new SyntaxError("SyntaxError at line " + t.lineNum() + ": Immediate out of range");
        }

        byte pc = (byte) value;

        int choice = 0;

        if (opcode == Opcode.JNE) choice = 1;

        byte code = (byte) (opcode.bits() << 4 | choice << 2);

        return new byte[] {code, pc};

    }

    private byte[] encodeImm(Opcode opcode, Token t) {
        String arg1 = t.arg1();
        String arg2 = t.arg2().toUpperCase();
        byte choice = 0;

        switch (opcode) {
            case MOV_IMM_REG -> choice = 0;

            case ADD_IMM_REG -> choice = 1;

            case AND_IMM_REG -> choice = 2;

            case SUB_IMM_REG -> choice = 3;
        }

        Register dest = Register.valueOf(arg2);

        int value = Integer.parseInt(arg1);

        if (value < -128 || value > 255) {
            throw new SyntaxError("SyntaxError at line " + t.lineNum() + ": Immediate out of range");
        }

        byte imm = (byte) value;
        byte code = (byte) (opcode.bits() << 4 | choice << 2 | dest.bits());

        return new byte[] {code, imm};
    }

    private byte[] encodeRet(Opcode opcode, Token t) {
        byte code = (byte) (opcode.bits() << 4);

        return new byte[] {code};
    }



}
