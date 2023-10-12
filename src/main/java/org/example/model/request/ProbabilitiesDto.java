package org.example.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProbabilitiesDto {

    @JsonProperty("standard_symbols")
    private List<ProbabilitiesSymbolDto> standardSymbols;
    @JsonProperty("bonus_symbols")
    private ProbabilitiesSymbolDto bonusSymbols;
}
