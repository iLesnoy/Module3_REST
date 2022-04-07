package com.epam.esm.gifts.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class GiftCertificate extends AbstractEntity {
    private String name;
    private String description;
    private BigDecimal price;
    private int duration;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private List<Tag> tags;

    {
        tags = new ArrayList<>();
    }


    public GiftCertificate(long id, String name, String description, BigDecimal price, int duration
            , LocalDateTime createDate, LocalDateTime lastUpdateDate, List<Tag> tags) {
        super(id);
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.tags = tags;
    }

    public boolean addTag(Tag tag) {
        return tags.add(tag);
    }

    @Override
    public boolean equals(Object otherObject) {
        if (!super.equals(otherObject)) {
            return false;
        }
        GiftCertificate other = (GiftCertificate) otherObject;
        if (name != null ? !name.equals(other.name) : other.name != null) {
            return false;
        }
        if (description != null ? !description.equals(other.description) : other.description != null) {
            return false;
        }
        if (price != null ? !price.equals(other.price) : other.price != null) {
            return false;
        }
        if (duration != other.duration) {
            return false;
        }
        if (createDate != null ? !createDate.equals(other.createDate) : other.createDate != null) {
            return false;
        }
        if (lastUpdateDate != null ? !lastUpdateDate.equals(other.lastUpdateDate) : other.lastUpdateDate != null) {
            return false;
        }
        return tags != null ? tags.equals(other.tags) : other.tags == null;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (name != null ? name.hashCode() : 0);
        result = prime * result + (description != null ? description.hashCode() : 0);
        result = prime * result + (price != null ? price.hashCode() : 0);
        result = prime * result + (duration);
        result = prime * result + (createDate != null ? createDate.hashCode() : 0);
        result = prime * result + (lastUpdateDate != null ? lastUpdateDate.hashCode() : 0);
        result = prime * result + (tags != null ? tags.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(super.toString());
        result.append(" ,name = ").append(name);
        result.append(" ,description = ").append(description);
        result.append(" ,price = ").append(price);
        result.append(" ,duration = ").append(duration);
        result.append(" ,createDate = ").append(createDate);
        result.append(" ,lastUpdateDate = ").append(lastUpdateDate);
        result.append(" ,tags = ").append(tags).append(" ]");
        return result.toString();
    }
}
