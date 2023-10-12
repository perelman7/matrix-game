package org.example.service.reward;

import org.example.model.request.GameConfigDto;
import org.example.model.response.RewardInfo;
import org.example.service.combination.WinCombinationCounter;
import org.example.service.combination.impl.SymbolAmountWinCombinationCounter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.example.util.FileReader.parseFromFile;
import static org.junit.jupiter.api.Assertions.*;

class RewardCounterServiceTest {

    RewardCounterService rewardCounterService = new RewardCounterService();
    private static String[][] matrix = new String[3][3];

    @BeforeAll
    static void init() {
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                if (column == row) {
                    matrix[row][column] = "A";
                } else if (column == 1) {
                    matrix[row][column] = "B";
                } else {
                    matrix[row][column] = "C";
                }
            }
        }
        matrix[2][1] = "+500";
    }

    @Test
    void countCondition() {
        GameConfigDto gameConfigDto = parseFromFile("./config.json");
        Map<String, List<String>> combinations = new HashMap<>();
        combinations.put("A", List.of("same_symbol_3_times", "same_symbols_diagonally_left_to_right"));
        combinations.put("C", Collections.singletonList("same_symbol_4_times"));
        RewardInfo rewardInfo = rewardCounterService.countReward(matrix, gameConfigDto, combinations, 50);
        Assertions.assertEquals(rewardInfo.getReward(), 13750);
        Assertions.assertEquals(rewardInfo.getAppliedBonusSymbol(), "+500");
    }
}