package org.example.service.combination.impl;

import org.example.model.request.GameConfigDto;
import org.example.service.combination.WinCombinationCounter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.example.util.FileReader.parseFromFile;
import static org.junit.jupiter.api.Assertions.*;

class HorizontallyRowWinCombinationCounterTest {

    WinCombinationCounter combinationCounter = new HorizontallyRowWinCombinationCounter();
    private static String[][] matrix = new String[3][3];

    @BeforeAll
    static void init() {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                if (row == 0) {
                    matrix[row][column] = "A";
                } else if (row == 1) {
                    matrix[row][column] = "B";
                } else {
                    matrix[row][column] = "C";
                }
            }
        }
    }

    @Test
    void countCondition() {
        GameConfigDto gameConfigDto = parseFromFile("./config.json");
        Map<String, List<String>> combinations = new HashMap<>();
        combinationCounter.countCondition(matrix, gameConfigDto, combinations);
        Assertions.assertEquals(combinations.size(), 3);
        Assertions.assertEquals(combinations.get("A").get(0), "same_symbols_horizontally");

        matrix[0][0] = "F";
        matrix[1][0] = "D";
        combinations.clear();
        combinationCounter.countCondition(matrix, gameConfigDto, combinations);
        Assertions.assertEquals(combinations.size(), 1);
        Assertions.assertTrue(combinations.containsKey("C"));
        Assertions.assertFalse(combinations.containsKey("A"));
    }
}