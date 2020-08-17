package org.kitchen.booting.domain;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AutoCompleteDTO {
    private String title;
    private String thumbnail;
    private Long recipeNo;
    public AutoCompleteDTO(String title, String thumbnail,Long recipeNo){
        this.title = title;
        this.thumbnail = thumbnail;
        this.recipeNo = recipeNo;
    }
}
