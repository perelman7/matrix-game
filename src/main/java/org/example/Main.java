package org.example;

import org.example.model.response.GameResultDto;
import org.example.service.GameService;
import org.example.util.PropUtil;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Map<String, String> props = PropUtil.init(args);
//        Map<String, String> props = new HashMap<>();
//        props.put("--config", "config.json");
//        props.put("--betting-amount", "100");
        System.out.println("INPUT:\n" + props + "\n");

        GameResultDto execute = new GameService()
                .execute(PropUtil.getConfig(props),
                        PropUtil.getBetAmount(props));
        System.out.println("\nOUTPUT:\n" + execute.toCuteJson());
    }
}