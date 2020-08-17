package org.kitchen.booting.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class TagDTO {
    private Long tagNo;
    private String content;


    public TagDTO(Long t, String content) {
        this.tagNo = t;
        this.content = content;
    }
}