package org.example.service.combination.impl;

import org.example.model.request.GameConfigDto;
import org.example.service.combination.WinCombinationCounter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VerticallyColumnWinCombinationCounter implements WinCombinationCounter {
    private final String COMBINATION_NAME = "same_symbols_vertically";

    @Override
    public void countCondition(String[][] matrix, GameConfigDto configDto,
                               Map<String, List<String>> combinations) {
        int column = 0;
        int maxColumn = 0;
        do {
            int equalColumn = 0;
            for (int row = 0; row < matrix.length; row++) {
                maxColumn = matrix[row].length;
                if (row != matrix.length - 1
                        && isInCoveredAreas(COMBINATION_NAME, configDto, row, column)
                        && isInCoveredAreas(COMBINATION_NAME, configDto, row + 1, column)) {
                    equalColumn = matrix[row][column].equals(matrix[row + 1][column]) ?
                            equalColumn + 1 :
                            equalColumn;
                }
            }
            if (equalColumn == matrix.length - 1) {
                List<String> orDefault = combinations.getOrDefault(matrix[0][column], new ArrayList<>());
                orDefault.add(COMBINATION_NAME);
                combinations.put(matrix[0][column], orDefault);
            }
            column++;
        } while (column != maxColumn);
    }
}
