package io.github.jsong2958.toyassembler.symbol;

import io.github.jsong2958.toyassembler.parser.Token;
import io.github.jsong2958.toyassembler.error.SyntaxError;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {

    private Map<String, Integer> table;


    public SymbolTable() {
        this.table = new HashMap<>();
    }

    public void mapLabel(Token t, int pc) {

        String label = t.label();
        if (table.containsKey(label)) {
            throw new SyntaxError("SyntaxError at line " + t.lineNum() + ": Already have label\n" + label);
        }

        table.put(label, pc);
    }

    public Integer get(String label) {
        if (!table.containsKey(label)) return null;

        return table.get(label);

    }

    public boolean containsKey(String label) { return table.containsKey(label); }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SymbolTable{\n");

        for (Map.Entry<String, Integer> entry : table.entrySet()) {
            sb.append("  ")
                    .append(entry.getKey())
                    .append(" -> ")
                    .append(entry.getValue())
                    .append("\n");
        }

        sb.append("}");
        return sb.toString();
    }

}
