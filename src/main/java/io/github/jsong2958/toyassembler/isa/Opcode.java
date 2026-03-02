package io.github.jsong2958.toyassembler.isa;

public enum Opcode {
    MOV(2,0,0),
    ADD(2,0,0),
    AND(2,0,0),
    SUB(2, 0, 0),
    MOV_REG_REG(2, 1, 0b0000),
    ADD_REG_REG(2, 1, 0b0001),
    AND_REG_REG(2, 1, 0b0010),
    NEG(1, 1, 0b0011),
    NOT(1, 1, 0b0011),
    LNOT(1, 1, 0b0011),
    CLEAR(1, 1, 0b0011),
    JMP(1, 2, 0b0100),
    MOV_IMM_REG(2, 2, 0b0101),
    ADD_IMM_REG(2, 2, 0b0101),
    AND_IMM_REG(2, 2, 0b0101),
    SUB_IMM_REG(2, 2, 0b0101),
    WRITE(2, 1, 0b0110),
    READ(2, 1, 0b0111),
    PUSH(1, 1, 0b1000),
    POP(1, 1, 0b1000),
    CALL(1, 2, 0b1001),
    RET(0, 1, 0b1010),
    SUB_REG_REG(2, 1, 0b1011),
    CMP(2, 1, 0b1100),
    JE(1, 2, 0b1101),
    JNE(1, 2, 0b1101),
    XOR(2, 1, 0b1110),
    OR(2, 1, 0b1111);

    private final int argCount;
    private final int width;
    private final int bits;

    Opcode(int argCount, int width, int bits) {
        this.argCount = argCount;
        this.width = width; //bytes
        this.bits = bits;
    }

    public int getArgCount() { return argCount; }

    public int width() { return width; }

    public int bits() { return bits; }
}
