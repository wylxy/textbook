package com.sheepion.model;

public class TargetGroup {
    private Integer id;

    private Integer residentId;

    private Integer categoryId;

    private Integer streetId;

    private Integer responsiblePerson;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getResidentId() {
        return residentId;
    }

    public void setResidentId(Integer residentId) {
        this.residentId = residentId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getStreetId() {
        return streetId;
    }

    public void setStreetId(Integer streetId) {
        this.streetId = streetId;
    }

    public Integer getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(Integer responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", residentId=").append(residentId);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", streetId=").append(streetId);
        sb.append(", responsiblePerson=").append(responsiblePerson);
        sb.append("]");
        return sb.toString();
    }
}