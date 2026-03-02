package io.github.jsong2958.toyassembler.isa;

public enum Register {
    R0(0b00),
    R1(0b01),
    R2(0b10),
    R3(0b11);

    private final int bits;

    Register(int bits) {
        this.bits = bits;
    }

    public int bits() { return this.bits; }
}
