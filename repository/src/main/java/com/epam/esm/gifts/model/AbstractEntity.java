package com.epam.esm.gifts.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public abstract class AbstractEntity {

    private long id;


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(getClass().getSimpleName());
        result.append("[ id = ").append(id);
        return result.toString();
    }
}
