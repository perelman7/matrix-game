package org.example.service.combination.impl;

import org.example.model.request.GameConfigDto;
import org.example.model.request.WinCombinationDto;
import org.example.service.combination.WinCombinationCounter;

import java.util.*;

public class DiagonallyWinCombinationCounter implements WinCombinationCounter {

    private final String diagonallyLR = "same_symbols_diagonally_left_to_right";
    private final String diagonallyRL = "same_symbols_diagonally_right_to_left";
    private final String colon = ":";

    @Override
    public void countCondition(String[][] matrix, GameConfigDto configDto,
                               Map<String, List<String>> combinations) {
        WinCombinationDto diagonallyLRWinCombinationDto = configDto.getWinCombinations().get(diagonallyLR);
        Set<String> symbolsLR = retrieveSymbols(matrix, diagonallyLRWinCombinationDto);
        checkAndAssignCombination(symbolsLR, combinations, diagonallyLR);

        WinCombinationDto diagonallyRLWinCombinationDto = configDto.getWinCombinations().get(diagonallyRL);
        Set<String> symbolsRL = retrieveSymbols(matrix, diagonallyRLWinCombinationDto);
        checkAndAssignCombination(symbolsRL, combinations, diagonallyRL);
    }

    private Set<String> retrieveSymbols(String[][] matrix, WinCombinationDto winCombinationDto) {
        Set<String> symbols = new HashSet<>();
        for (String[] coveredAreaOuter : winCombinationDto.getCoveredAreas()) {
            for (String coveredAreaInner : coveredAreaOuter) {
                String[] split = coveredAreaInner.split(colon);
                int row = Integer.parseInt(split[0]);
                int column = Integer.parseInt(split[1]);
                symbols.add(matrix[row][column]);
            }
        }
        return symbols;
    }

    private void checkAndAssignCombination(Set<String> symbols,
                                                  Map<String, List<String>> combinations,
                                                  String combinationName) {
        if (symbols.size() == 1) {
            symbols.stream().findFirst().ifPresent(first -> {
                List<String> combinationList = combinations.getOrDefault(first, new ArrayList<>());
                combinationList.add(combinationName);
                combinations.put(first, combinationList);
            });
        }
    }
}
