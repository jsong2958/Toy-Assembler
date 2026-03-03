# Toy Assembly Opcodes

Syntax

    LABEL: OPCODE SRC, DEST ;COMMENT
**WRITE: writes the value of src to the address stored at dest**

**READ: reads the value at the address stored in src into dest**

| Opcode        | Arguments         
|---------------|-----------------
| `MOV`         | Register, Register or Immediate, Register
| `ADD`         | Register, Register or Immediate, Register
| `SUB`         | Register, Register or Immediate, Register   
| `AND`         | Register, Register or Immediate, Register     
| `XOR`         | Register, Register
| `OR`         | Register, Register
| `NEG`         | Register
| `NOT`         | Register
| `LNOT`         | Register
| `CLEAR`         | Register     
| `JMP`         | Label   
| `WRITE`         | Register, Register
| `READ`         | Register, Register
| `PUSH`         | Register
| `POP`         | Register
| `CALL`         | Label 
| `RET`         |  None
| `HALT`         | None
| `CMP`         | Register, Register
| `JE`         | Label 
| `JNE`         | Label 

