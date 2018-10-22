package com.lexue.bp.common.entity.pk;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class RulePK implements Serializable {

    @Getter
    @Setter
    private int ruleId;

    @Getter @Setter
    private int moduleId;
}
