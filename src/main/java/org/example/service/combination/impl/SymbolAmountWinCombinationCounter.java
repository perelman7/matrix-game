package org.example.service.combination.impl;

import org.example.model.request.GameConfigDto;
import org.example.model.request.WinCombinationDto;
import org.example.service.combination.WinCombinationCounter;

import java.util.*;

public class SymbolAmountWinCombinationCounter implements WinCombinationCounter {

    private final String sameSymbol = "same_symbols";

    @Override
    public void countCondition(String[][] matrix, GameConfigDto configDto,
                               Map<String, List<String>> combinations) {

        Map<String, Integer> symbolCount = new HashMap<>();
        for (int row = 0; row < matrix.length; row++) {
            for (int column = 0; column < matrix[row].length; column++) {
                String currentSymbol = matrix[row][column];
                Integer count = symbolCount.getOrDefault(currentSymbol, 0);
                symbolCount.put(currentSymbol, ++count);
            }
        }
        for (Map.Entry<String, Integer> entry : symbolCount.entrySet()) {
            if (entry.getValue() >= 3) {
                for (Map.Entry<String, WinCombinationDto> combination : configDto.getWinCombinations().entrySet()) {
                    WinCombinationDto currentCombination = combination.getValue();
                    if (Objects.equals(currentCombination.getGroup(), sameSymbol)
                            && Objects.equals(entry.getValue(), currentCombination.getCount())) {
                        List<String> currentCombinations = combinations.getOrDefault(entry.getKey(), new ArrayList<>());
                        currentCombinations.add(combination.getKey());
                        combinations.put(entry.getKey(), currentCombinations);
                    }
                }
            }
        }
    }
}
