package org.example.model.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class GameConfigDto {

    private int columns;
    private int rows;
    private Map<String, SymbolInfoDto> symbols;
    private ProbabilitiesDto probabilities;
    @JsonProperty("win_combinations")
    private Map<String, WinCombinationDto> winCombinations;

}
