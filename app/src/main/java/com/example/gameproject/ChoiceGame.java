package com.example.gameproject;

import java.util.HashSet;
import java.util.Set;

public enum ChoiceGame {
    GAME_HUNG_BIA("Game hứng bia"),
    GAME_CHIM_BAY("Game chim bay");

    private final String UIName;
    private final Set<String> UINameSet = new HashSet<>();

    ChoiceGame(String UIName) {
        if(!this.UINameSet.add(UIName)){
            throw new IllegalArgumentException("Name is duplicate");
        }else{
            this.UINameSet.add(UIName);
            this.UIName = UIName;
        }
    }

    public String getUIName(){
        return UIName;
    }

    public static ChoiceGame findByUIName(String UIName) throws Exception{
        for(ChoiceGame choiceGame: values()){
            if (choiceGame.getUIName().equals(UIName)){
                return  choiceGame;
            }
        }
        throw new IllegalArgumentException("UIName invalid: "+UIName);
    }
}
