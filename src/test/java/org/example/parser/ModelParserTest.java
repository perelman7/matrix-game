package org.example.parser;

import org.example.model.request.GameConfigDto;
import org.example.model.response.GameResultDto;
import org.example.util.ModelParserUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ModelParserTest {

    ModelParserUtil modelParser = new ModelParserUtil();

    @Test
    void parse() {
        GameConfigDto parse = modelParser.parse(jsont, GameConfigDto.class);
        Assertions.assertEquals(parse.getColumns(), 3);
        Assertions.assertEquals(parse.getRows(), 3);
        Assertions.assertEquals(parse.getSymbols().get("B").getRewardMultiplier(), 25);
    }

    @Test
    void testNullJsonError() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            modelParser.parse(null, GameConfigDto.class);
        });
    }

    @Test
    void testParseError() {
        GameConfigDto parse = modelParser.parse("[" + jsont, GameConfigDto.class);
        Assertions.assertNull(parse);
    }

    @Test
    void testRespParse(){
        GameResultDto parse = modelParser.parse(respJson, GameResultDto.class);
        Assertions.assertEquals(parse.getMatrix()[0][1], "A");
        Assertions.assertEquals(parse.getMatrix()[1][1], "+1000");
    }

    private static String respJson = "{\n" +
            "    \"matrix\": [\n" +
            "        [\"A\", \"A\", \"B\"],\n" +
            "        [\"A\", \"+1000\", \"B\"],\n" +
            "        [\"A\", \"A\", \"B\"]\n" +
            "    ],\n" +
            "    \"reward\": 6600,\n" +
            "    \"applied_winning_combinations\": {\n" +
            "        \"A\": [\"same_symbol_5_times\", \"same_symbols_vertically\"],\n" +
            "        \"B\": [\"same_symbol_3_times\", \"same_symbols_vertically\"]\n" +
            "    },\n" +
            "    \"applied_bonus_symbol\": \"+1000\"\n" +
            "}";


    private static String jsont = "{\n" +
            "   \"columns\":3,\n" +
            "   \"rows\":3,\n" +
            "   \"symbols\":{\n" +
            "      \"A\":{\n" +
            "         \"reward_multiplier\":50,\n" +
            "         \"type\":\"standard\"\n" +
            "      },\n" +
            "      \"B\":{\n" +
            "         \"reward_multiplier\":25,\n" +
            "         \"type\":\"standard\"\n" +
            "      },\n" +
            "      \"C\":{\n" +
            "         \"reward_multiplier\":10,\n" +
            "         \"type\":\"standard\"\n" +
            "      },\n" +
            "      \"D\":{\n" +
            "         \"reward_multiplier\":5,\n" +
            "         \"type\":\"standard\"\n" +
            "      },\n" +
            "      \"E\":{\n" +
            "         \"reward_multiplier\":3,\n" +
            "         \"type\":\"standard\"\n" +
            "      },\n" +
            "      \"F\":{\n" +
            "         \"reward_multiplier\":1.5,\n" +
            "         \"type\":\"standard\"\n" +
            "      },\n" +
            "      \"10x\":{\n" +
            "         \"reward_multiplier\":10,\n" +
            "         \"type\":\"bonus\",\n" +
            "         \"impact\":\"multiply_reward\"\n" +
            "      },\n" +
            "      \"5x\":{\n" +
            "         \"reward_multiplier\":5,\n" +
            "         \"type\":\"bonus\",\n" +
            "         \"impact\":\"multiply_reward\"\n" +
            "      },\n" +
            "      \"+1000\":{\n" +
            "         \"extra\":1000,\n" +
            "         \"type\":\"bonus\",\n" +
            "         \"impact\":\"extra_bonus\"\n" +
            "      },\n" +
            "      \"+500\":{\n" +
            "         \"extra\":500,\n" +
            "         \"type\":\"bonus\",\n" +
            "         \"impact\":\"extra_bonus\"\n" +
            "      },\n" +
            "      \"MISS\":{\n" +
            "         \"type\":\"bonus\",\n" +
            "         \"impact\":\"miss\"\n" +
            "      }\n" +
            "   },\n" +
            "   \"probabilities\":{\n" +
            "      \"standard_symbols\":[\n" +
            "         {\n" +
            "            \"column\":0,\n" +
            "            \"row\":0,\n" +
            "            \"symbols\":{\n" +
            "               \"A\":1,\n" +
            "               \"B\":2,\n" +
            "               \"C\":3,\n" +
            "               \"D\":4,\n" +
            "               \"E\":5,\n" +
            "               \"F\":6\n" +
            "            }\n" +
            "         },\n" +
            "         {\n" +
            "            \"column\":0,\n" +
            "            \"row\":1,\n" +
            "            \"symbols\":{\n" +
            "               \"A\":1,\n" +
            "               \"B\":2,\n" +
            "               \"C\":3,\n" +
            "               \"D\":4,\n" +
            "               \"E\":5,\n" +
            "               \"F\":6\n" +
            "            }\n" +
            "         }\n" +
            "      ],\n" +
            "      \"bonus_symbols\":{\n" +
            "         \"symbols\":{\n" +
            "            \"10x\":1,\n" +
            "            \"5x\":2,\n" +
            "            \"+1000\":3,\n" +
            "            \"+500\":4,\n" +
            "            \"MISS\":5\n" +
            "         }\n" +
            "      }\n" +
            "   },\n" +
            "   \"win_combinations\":{\n" +
            "      \"same_symbol_3_times\":{\n" +
            "         \"reward_multiplier\":1,\n" +
            "         \"when\":\"same_symbols\",\n" +
            "         \"count\":3,\n" +
            "         \"group\":\"same_symbols\"\n" +
            "      },\n" +
            "      \"same_symbol_4_times\":{\n" +
            "         \"reward_multiplier\":1.5,\n" +
            "         \"when\":\"same_symbols\",\n" +
            "         \"count\":4,\n" +
            "         \"group\":\"same_symbols\"\n" +
            "      },\n" +
            "      \"same_symbol_5_times\":{\n" +
            "         \"reward_multiplier\":2,\n" +
            "         \"when\":\"same_symbols\",\n" +
            "         \"count\":5,\n" +
            "         \"group\":\"same_symbols\"\n" +
            "      },\n" +
            "      \"same_symbol_6_times\":{\n" +
            "         \"reward_multiplier\":3,\n" +
            "         \"when\":\"same_symbols\",\n" +
            "         \"count\":6,\n" +
            "         \"group\":\"same_symbols\"\n" +
            "      },\n" +
            "      \"same_symbol_7_times\":{\n" +
            "         \"reward_multiplier\":5,\n" +
            "         \"when\":\"same_symbols\",\n" +
            "         \"count\":7,\n" +
            "         \"group\":\"same_symbols\"\n" +
            "      },\n" +
            "      \"same_symbol_8_times\":{\n" +
            "         \"reward_multiplier\":10,\n" +
            "         \"when\":\"same_symbols\",\n" +
            "         \"count\":8,\n" +
            "         \"group\":\"same_symbols\"\n" +
            "      },\n" +
            "      \"same_symbol_9_times\":{\n" +
            "         \"reward_multiplier\":20,\n" +
            "         \"when\":\"same_symbols\",\n" +
            "         \"count\":9,\n" +
            "         \"group\":\"same_symbols\"\n" +
            "      },\n" +
            "      \"same_symbols_horizontally\":{\n" +
            "         \"reward_multiplier\":2,\n" +
            "         \"when\":\"linear_symbols\",\n" +
            "         \"group\":\"horizontally_linear_symbols\",\n" +
            "         \"covered_areas\":[\n" +
            "            [\n" +
            "               \"0:0\",\n" +
            "               \"0:1\",\n" +
            "               \"0:2\"\n" +
            "            ],\n" +
            "            [\n" +
            "               \"1:0\",\n" +
            "               \"1:1\",\n" +
            "               \"1:1\"\n" +
            "            ],\n" +
            "            [\n" +
            "               \"2:0\",\n" +
            "               \"2:1\",\n" +
            "               \"2:2\"\n" +
            "            ]\n" +
            "         ]\n" +
            "      },\n" +
            "      \"same_symbols_vertically\":{\n" +
            "         \"reward_multiplier\":2,\n" +
            "         \"when\":\"linear_symbols\",\n" +
            "         \"group\":\"vertically_linear_symbols\",\n" +
            "         \"covered_areas\":[\n" +
            "            [\n" +
            "               \"0:0\",\n" +
            "               \"1:0\",\n" +
            "               \"2:0\"\n" +
            "            ],\n" +
            "            [\n" +
            "               \"0:1\",\n" +
            "               \"1:1\",\n" +
            "               \"2:1\"\n" +
            "            ],\n" +
            "            [\n" +
            "               \"0:2\",\n" +
            "               \"1:2\",\n" +
            "               \"2:2\"\n" +
            "            ]\n" +
            "         ]\n" +
            "      },\n" +
            "      \"same_symbols_diagonally_left_to_right\":{\n" +
            "         \"reward_multiplier\":5,\n" +
            "         \"when\":\"linear_symbols\",\n" +
            "         \"group\":\"ltr_diagonally_linear_symbols\",\n" +
            "         \"covered_areas\":[\n" +
            "            [\n" +
            "               \"0:0\",\n" +
            "               \"1:1\",\n" +
            "               \"2:2\"\n" +
            "            ]\n" +
            "         ]\n" +
            "      },\n" +
            "      \"same_symbols_diagonally_right_to_left\":{\n" +
            "         \"reward_multiplier\":5,\n" +
            "         \"when\":\"linear_symbols\",\n" +
            "         \"group\":\"rtl_diagonally_linear_symbols\",\n" +
            "         \"covered_areas\":[\n" +
            "            [\n" +
            "               \"0:2\",\n" +
            "               \"1:1\",\n" +
            "               \"2:0\"\n" +
            "            ]\n" +
            "         ]\n" +
            "      }\n" +
            "   }\n" +
            "}";
}