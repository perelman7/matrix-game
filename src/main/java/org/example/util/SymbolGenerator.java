package org.example.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SymbolGenerator {
    private final Random random = new Random();

    public String generateSymbol(Map<String, Integer> symbols) {
        Map<String, Double> tempSymbols = this.tempSymbol(symbols);

        int procentage = random.nextInt(100);
        String element = null;
        for (Map.Entry<String, Double> entry : tempSymbols.entrySet()) {
            if (entry.getValue() > procentage) {
                element = entry.getKey();
                break;
            }
        }
        return element;
    }

    private Map<String, Double> tempSymbol(Map<String, Integer> symbols){
        Integer sum = symbols.values().stream().reduce(0, Integer::sum);
        Map<String, Double> tempSymbols = new HashMap<>();
        double previousState = 0D;
        for (Map.Entry<String, Integer> entry : symbols.entrySet()) {
            previousState += Double.valueOf(entry.getValue()) / sum * 100;
            tempSymbols.put(entry.getKey(), previousState);
        }
        return tempSymbols;
    }
}
