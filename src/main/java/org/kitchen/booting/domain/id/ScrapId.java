package org.kitchen.booting.domain.id;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.kitchen.booting.domain.Recipe;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class ScrapId implements Serializable {
    private String user;
    private Long recipe;
}
