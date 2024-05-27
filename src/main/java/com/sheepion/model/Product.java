package com.sheepion.model;

public class Product {
    private Integer productId;
    /**
     * productName
     */
    private String productName;
    private String giftsName;
    private String orderDate;

    private String orderPrice;

    private String orderNum;


    /**
     * 生产期
     */
    private String productDate;

    /**
     * productPeriod
     */
    private String productPeriod;

    /**
     * agePeriod
     */
    private String agePeriod;
    private String customerName;
    private String orderTotal;
    private String evaluateRate;
    private String evaluateGrade;

    private String flag;
    private String comment;
    private String notes;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getEvaluateRate() {
        return evaluateRate;
    }

    public void setEvaluateRate(String evaluateRate) {
        this.evaluateRate = evaluateRate;
    }

    public String getEvaluateGrade() {
        return evaluateGrade;
    }

    public void setEvaluateGrade(String evaluateGrade) {
        this.evaluateGrade = evaluateGrade;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * suitablePopulation
     */
    private String suitablePopulation;

    public String getGiftsName() {
        return giftsName;
    }

    public void setGiftsName(String giftsName) {
        this.giftsName = giftsName;
    }

    /**
     * productType
     */
    private String productType;

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDate() {
        return productDate;
    }

    public void setProductDate(String productDate) {
        this.productDate = productDate;
    }

    public String getProductPeriod() {
        return productPeriod;
    }

    public void setProductPeriod(String productPeriod) {
        this.productPeriod = productPeriod;
    }

    public String getAgePeriod() {
        return agePeriod;
    }

    public void setAgePeriod(String agePeriod) {
        this.agePeriod = agePeriod;
    }

    public String getSuitablePopulation() {
        return suitablePopulation;
    }

    public void setSuitablePopulation(String suitablePopulation) {
        this.suitablePopulation = suitablePopulation;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }
}
