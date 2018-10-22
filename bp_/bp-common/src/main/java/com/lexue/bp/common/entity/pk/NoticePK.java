package com.lexue.bp.common.entity.pk;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class NoticePK implements Serializable {

    @Getter @Setter
    private long id;

    @Getter @Setter
    private int moduleId;

}
