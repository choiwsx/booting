package org.kitchen.booting.domain.id;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class StepId implements Serializable {
    private Long stepNo;
    private Long recipe;
}
