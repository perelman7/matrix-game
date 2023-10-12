package org.example.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class PropUtil {
    public static final String CONFIG = "--config";
    public static final String BETTING_AMOUNT = "--betting-amount";

    public static Map<String, String> init(String[] args){
        if(args.length != 4){
            throw new RuntimeException("You must assign two props in format: (--config config.json --betting-amount 100)");
        }
        Map<String, String> props = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.startsWith("--")) {
                props.put(arg, args[i + 1]);
            }
        }
        if(validation(props)){
            return props;
        }
        return null;
    }

    public static int getBetAmount(Map<String, String> props){
        return Integer.parseInt(props.get(BETTING_AMOUNT));
    }

    public static String getConfig(Map<String, String> props){
        return props.get(CONFIG);
    }

    private static boolean validation(Map<String, String> props){
        if (props.containsKey(CONFIG) && props.containsKey(BETTING_AMOUNT)){
            getBetAmount(props);
            String filePath = props.get(CONFIG);
            return new File(filePath).exists();
        }
        throw new RuntimeException("You must assign two props in format: (--config config.json --betting-amount 100)");
    }
}
