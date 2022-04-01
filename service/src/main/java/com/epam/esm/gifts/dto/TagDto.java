package com.epam.esm.gifts.dto;

import org.springframework.stereotype.Component;

@Component
public class TagDto {
    private long id;
    private String name;

    public TagDto() {
    }

    public TagDto(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null || getClass() != otherObject.getClass()) {
            return false;
        }
        TagDto other = (TagDto) otherObject;
        if (id != other.id) {
            return false;
        }
        return name != null ? name.equals(other.name) : other.name == null;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = (int) (id ^ (id >>> 32));
        result = prime * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(getClass().getSimpleName());
        result.append("[ id = ").append(id);
        result.append(" ,name = ").append(name).append(" ]");
        return result.toString();
    }
}