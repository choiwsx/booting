package org.kitchen.booting.domain.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum CookingTime {
    WITHIN_5MINS(1,"5분 이내" ), WITHIN_10MINS(2, "10분 이내"), WITHIN_15MINS(3,"15분 이내"),
    WITHIN_20MINS(4,"20분 이내"), WITHIN_30MINS(5,"30분 이내"), WITHIN_60MINS(6,"60분 이내"),
    WITHIN_90MINS(7,"90분 이내"), WITHIN_2HOURS(8,"2시간 이내"), OVER_2HOURS(9,"2시간 이상");

    private int code;
    private String time;

    CookingTime(int code)
    {
        this.code = code;
        this.time = getCookingTime(code).getTime();
    }

    CookingTime(int code, String time)
    {
        this.time = time;
        this.code = code;
    }

    public String getTime(){
        return this.time;
    }

    @JsonValue
    public int getCode() {
        return code;
    }

    public boolean hasCode(int code){
        return this.code==code;
    }

    public static CookingTime getCookingTime(int code)
    {
        return
        Arrays.stream(CookingTime.values()).filter(cookingTime->cookingTime.hasCode(code)).findAny().orElse(null);
    }

    @Override
    public String toString() {
        return time;
    }
}
