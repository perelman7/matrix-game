package org.example.service.combination.impl;

import org.example.model.request.GameConfigDto;
import org.example.service.combination.WinCombinationCounter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HorizontallyRowWinCombinationCounter implements WinCombinationCounter {
    private final String COMBINATION_NAME = "same_symbols_horizontally";

    @Override
    public void countCondition(String[][] matrix, GameConfigDto configDto,
                               Map<String, List<String>> combinations) {

        for (int row = 0; row < matrix.length; row++) {
            int equalRow = 0;
            for (int column = 0; column < matrix[row].length; column++) {
                if (column != matrix[row].length - 1
                        && isInCoveredAreas(COMBINATION_NAME, configDto, row, column)
                        && isInCoveredAreas(COMBINATION_NAME, configDto, row, column + 1)) {
                    equalRow = matrix[row][column].equals(matrix[row][column + 1]) ?
                            equalRow + 1 :
                            equalRow;
                }
            }
            if (equalRow == matrix[row].length - 1) {
                List<String> orDefault = combinations.getOrDefault(matrix[row][0], new ArrayList<>());
                orDefault.add(COMBINATION_NAME);
                combinations.put(matrix[row][0], orDefault);
            }
        }
    }
}
