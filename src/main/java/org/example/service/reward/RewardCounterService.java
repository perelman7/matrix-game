package org.example.service.reward;

import org.example.model.request.GameConfigDto;
import org.example.model.request.SymbolInfoDto;
import org.example.model.request.SymbolType;
import org.example.model.request.WinCombinationDto;
import org.example.model.response.ImpactType;
import org.example.model.response.BonusActionInfo;
import org.example.model.response.RewardInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RewardCounterService {

    //The reward count by next conditions:
    //1) count for every symbol there multiplied reward ("A" : bet * combination_1 * combination_2 ...)
    //then "B" : bet * combination_1 * combination_2 ...
    //2) Then sum each symbol reward (a_reward + b_reward + ...)
    //3) And then execute bonus reward (multiply or sum) a_b_..._reward +/* bonus_reward
    public RewardInfo countReward(String[][] matrix, GameConfigDto configDto,
                           Map<String, List<String>> combinations, int betAmount) {

        List<Double> rewardMultiplier = this.countRewardMultiplier(configDto, combinations);
        BonusActionInfo bonusReward = this.countBonusField(matrix, configDto);
        Double reward = 0D;
        for (Double multiplier : rewardMultiplier) {
            reward += betAmount * multiplier;
        }
        String bonusSymbol = null;
        if (bonusReward.getImpactType() != null) {
            bonusSymbol = bonusReward.getSymbol();
            if (bonusReward.getImpactType() == ImpactType.extra_bonus) {
                reward += bonusReward.getAmount();
            } else if (bonusReward.getImpactType() == ImpactType.multiply_reward) {
                reward *= bonusReward.getAmount();
            }
        }
        return RewardInfo.builder()
                .appliedBonusSymbol(bonusSymbol)
                .reward(reward)
                .build();
    }

    private List<Double> countRewardMultiplier(GameConfigDto configDto,
                                                Map<String, List<String>> combinations) {
        List<Double> result = new ArrayList<>();
        for (Map.Entry<String, List<String>> combination : combinations.entrySet()) {
            SymbolInfoDto symbolInfoDto = configDto.getSymbols().get(combination.getKey());
            double multiplex = symbolInfoDto.getRewardMultiplier();
            for (String combName : combination.getValue()) {
                WinCombinationDto winCombinationDto = configDto.getWinCombinations().get(combName);
                multiplex *= winCombinationDto.getRewardMultiplier();
            }
            result.add(multiplex);
        }
        return result;
    }

    private BonusActionInfo countBonusField(String[][] matrix,
                                            GameConfigDto configDto) {
        BonusActionInfo bonusAction = new BonusActionInfo();
        for (String[] row : matrix) {
            for (String column : row) {
                SymbolInfoDto symbolInfoDto = configDto.getSymbols().get(column);
                if (symbolInfoDto != null && SymbolType.bonus == symbolInfoDto.getType()) {
                    bonusAction.setSymbol(column);
                    if (symbolInfoDto.getImpact() == ImpactType.extra_bonus) {
                        bonusAction.setImpactType(ImpactType.extra_bonus);
                        bonusAction.setAmount(symbolInfoDto.getExtra());
                        return bonusAction;
                    } else if (symbolInfoDto.getImpact() == ImpactType.multiply_reward) {
                        bonusAction.setImpactType(ImpactType.multiply_reward);
                        bonusAction.setAmount(symbolInfoDto.getRewardMultiplier());
                        return bonusAction;
                    } else if (symbolInfoDto.getImpact() == ImpactType.miss) {
                        bonusAction.setImpactType(ImpactType.miss);
                        return bonusAction;
                    } else {
                        throw new RuntimeException("UNKNOWN IMPACT TYPE: " + symbolInfoDto.getImpact());
                    }
                }
            }
        }
        return bonusAction;
    }
}
