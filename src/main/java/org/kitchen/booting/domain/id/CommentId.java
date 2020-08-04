package org.kitchen.booting.domain.id;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor
public class CommentId implements Serializable {


    private Long commentNo;
    private Long recipeNo;
}