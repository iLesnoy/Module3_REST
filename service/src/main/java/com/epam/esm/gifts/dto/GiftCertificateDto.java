package com.epam.esm.gifts.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class GiftCertificateDto {
    private long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int duration;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private List<TagDto> tags;

    public GiftCertificateDto() {
    }

    public GiftCertificateDto(long id, String name, String description, BigDecimal price, int duration
            , LocalDateTime createDate, LocalDateTime lastUpdateDate, List<TagDto> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createDate = createDate;
        this.lastUpdateDate = lastUpdateDate;
        this.tags = tags;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public List<TagDto> getTags() {
        return tags;
    }

    public void setTags(List<TagDto> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) {
            return true;
        }
        if (otherObject == null || getClass() != otherObject.getClass()) {
            return false;
        }
        GiftCertificateDto other = (GiftCertificateDto) otherObject;
        if (id != other.id) {
            return false;
        }
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
        int result = (int) (id ^ (id >>> 32));
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
        StringBuilder result = new StringBuilder(getClass().getSimpleName());
        result.append("[ id = ").append(id);
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