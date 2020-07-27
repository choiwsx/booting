package org.kitchen.booting.domain.id;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class MaterialId implements Serializable {
    private Long materialNo;
    private Long recipe;
}
