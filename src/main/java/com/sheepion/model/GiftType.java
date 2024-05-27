package com.sheepion.model;

public class GiftType {
    private Integer giftTypeId;

    private String giftTypeName;

    public Integer getGiftTypeId() {
        return giftTypeId;
    }

    public void setGiftTypeId(Integer giftTypeId) {
        this.giftTypeId = giftTypeId;
    }

    public String getGiftTypeName() {
        return giftTypeName;
    }

    public void setGiftTypeName(String giftTypeName) {
        this.giftTypeName = giftTypeName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", giftTypeId=").append(giftTypeId);
        sb.append(", giftTypeName=").append(giftTypeName);
        sb.append("]");
        return sb.toString();
    }
}
