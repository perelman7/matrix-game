package org.example.service.combination;

import org.example.model.request.GameConfigDto;
import org.example.model.request.WinCombinationDto;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface WinCombinationCounter {

    void countCondition(String[][] matrix, GameConfigDto configDto,
                        Map<String, List<String>> combinations);


    default boolean isInCoveredAreas(String combName, GameConfigDto configDto, int row, int column) {
        WinCombinationDto winCombinationDto = configDto.getWinCombinations().get(combName);
        String[][] coveredAreas = winCombinationDto.getCoveredAreas();
        for (String[] coveredArea : coveredAreas) {
            for (String area : coveredArea) {
                String[] coordinates = area.split(":");
                if (Objects.equals(Integer.parseInt(coordinates[0]), row)
                        && Objects.equals(Integer.parseInt(coordinates[1]), column)) {
                    return true;
                }
            }
        }
        return false;
    }
}
