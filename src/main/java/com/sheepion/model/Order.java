package com.sheepion.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Order {
    private Integer orderId;

    /**
     * customerId
     */
    private String customerId;

    /**
     * productId
     */
    private String productId;

    /**
     * giftsId
     */
    private String giftsId;

    /**
     * giftsStatus
     */

    private String giftsStatus;
    private String giftDate;

    public String getGiftDate() {
        return giftDate;
    }

    public void setGiftDate(String giftDate) {
        this.giftDate = giftDate;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getGiftsId() {
        return giftsId;
    }

    public void setGiftsId(String giftsId) {
        this.giftsId = giftsId;
    }

    public String getGiftsStatus() {
        return giftsStatus;
    }

    public void setGiftsStatus(String giftsStatus) {
        this.giftsStatus = giftsStatus;
    }
}
