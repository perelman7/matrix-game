package org.example.service;

import org.example.model.request.GameConfigDto;
import org.example.model.request.ProbabilitiesSymbolDto;
import org.example.model.response.GameResultDto;
import org.example.model.response.RewardInfo;
import org.example.service.combination.WinCombinationCounter;
import org.example.service.combination.impl.DiagonallyWinCombinationCounter;
import org.example.service.combination.impl.HorizontallyRowWinCombinationCounter;
import org.example.service.combination.impl.SymbolAmountWinCombinationCounter;
import org.example.service.combination.impl.VerticallyColumnWinCombinationCounter;
import org.example.service.reward.RewardCounterService;
import org.example.util.ModelParserUtil;
import org.example.util.SymbolGenerator;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class GameService {
    private final ModelParserUtil modelParser = new ModelParserUtil();
    private final SymbolGenerator symbolGenerator = new SymbolGenerator();
    private final RewardCounterService rewardCounterService = new RewardCounterService();
    private final Random random = new Random();

    List<WinCombinationCounter> winCounters = new ArrayList<>() {{
        add(new SymbolAmountWinCombinationCounter());
        add(new HorizontallyRowWinCombinationCounter());
        add(new VerticallyColumnWinCombinationCounter());
        add(new DiagonallyWinCombinationCounter());
    }};

    public GameResultDto execute(String filename, int betAmount) {
        GameConfigDto gameConfigDto = this.parseFromFile(filename);
        String[][] matrix = construct(gameConfigDto);
        for (String[] strings : matrix) {
            System.out.println(Arrays.toString(strings));
        }
        Map<String, List<String>> combinations = new HashMap<>();
        for (WinCombinationCounter winCounter : winCounters) {
            winCounter.countCondition(matrix, gameConfigDto, combinations);
        }
        GameResultDto resultDto = new GameResultDto();
        resultDto.setMatrix(matrix);
        resultDto.setReward(0);
        resultDto.setAppliedWinningCombinations(combinations);
        if (!combinations.isEmpty()) {
            RewardInfo rewardInfo = rewardCounterService.countReward(matrix, gameConfigDto, combinations, betAmount);
            resultDto.setReward((int) Math.round(rewardInfo.getReward()));
            rewardInfo.setAppliedBonusSymbol(resultDto.getAppliedBonusSymbol());
        }
        return resultDto;
    }

    private String[][] construct(GameConfigDto configDto) {
        String[][] matrix = new String[configDto.getRows()][configDto.getColumns()];
        for (ProbabilitiesSymbolDto standardSymbol : configDto.getProbabilities().getStandardSymbols()) {
            matrix[standardSymbol.getRow()][standardSymbol.getColumn()] = symbolGenerator
                    .generateSymbol(standardSymbol.getSymbols());
        }
        //in documentation 'bonus_symbols' described as list, but in json as one object
        //and Note (2): Bonus symbol can be generated randomly in any cell(s) in the matrix
        //I understand that like: 1) it may or may not be in matrix 2) the location is assigned randomly
        if (random.nextBoolean()) {
            int randomRow = random.nextInt(configDto.getRows());
            int randomColumn = random.nextInt(configDto.getColumns());
            String bonusSymbol = symbolGenerator.generateSymbol(configDto.getProbabilities().getBonusSymbols().getSymbols());
            matrix[randomRow][randomColumn] = bonusSymbol;
        }
        return matrix;
    }

    private GameConfigDto parseFromFile(String filename) {
        try (FileInputStream fileInputStream = new FileInputStream(filename)) {
            String json = new String(fileInputStream.readAllBytes());
            return modelParser.parse(json, GameConfigDto.class);
        } catch (IOException e) {
            throw new RuntimeException("Not valid config json: " + e.getMessage());
        }
    }
}
