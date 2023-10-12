package org.example.parser;

import org.example.util.SymbolGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

class SymbolGeneratorTest {

    private final SymbolGenerator symbolGenerator = new SymbolGenerator();
    Map<String, Integer> map = new HashMap<>(){{
        put("A", 1);
        put("B", 2);
        put("C", 3);
        put("D", 4);
        put("E", 5);
        put("F", 6);
    }};

    @Test
    void generateSymbol() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<String> all = new ArrayList<>();
        for(int i = 0; i < 10000; i ++){
            all.add(symbolGenerator.generateSymbol(map));
        }
        Method tempSymbol = symbolGenerator.getClass().getDeclaredMethod("tempSymbol", Map.class);
        tempSymbol.setAccessible(true);
        Map<String, Double> invoke = (Map<String, Double>)tempSymbol.invoke(symbolGenerator, map);
        Map<String, Long> collect = all.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        double previousState = 0;
        for (Map.Entry<String, Double> entry : invoke.entrySet()) {
            Double oldVal = entry.getValue();
            invoke.put(entry.getKey(), oldVal - previousState);
            previousState = oldVal;
        }
        for (Map.Entry<String, Long> entry : collect.entrySet()) {
            Double target = invoke.get(entry.getKey());
            Assertions.assertTrue(Math.abs(target - (double) entry.getValue() / 100) < 2);
        }
    }
}