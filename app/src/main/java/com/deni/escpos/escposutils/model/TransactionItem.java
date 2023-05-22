package com.deni.escpos.escposutils.model;

public class TransactionItem {
    String productName;
    int qty;
    Double sellingPrice;
    Double total;

    public TransactionItem(String productName, int qty, Double sellingPrice, Double total) {
        this.productName = productName;
        this.qty = qty;
        this.sellingPrice = sellingPrice;
        this.total = total;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(Double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
