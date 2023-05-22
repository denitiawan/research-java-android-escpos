package com.deni.escpos.escposutils.model;

import java.util.List;

public class Transaction {
    String invoiceNumber;
    String cashierName;
    List<TransactionItem> items;
    Double grossAmount;
    Double discount;
    Double netAmount;

    public Transaction() {

    }

    public Transaction(String invoiceNumber, String cashierName, List<TransactionItem> items, Double grossAmount, Double discount, Double netAmount) {
        this.invoiceNumber = invoiceNumber;
        this.cashierName = cashierName;
        this.items = items;
        this.grossAmount = grossAmount;
        this.discount = discount;
        this.netAmount = netAmount;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getCashierName() {
        return cashierName;
    }

    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
    }

    public List<TransactionItem> getItems() {
        return items;
    }

    public void setItems(List<TransactionItem> items) {
        this.items = items;
    }

    public Double getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(Double grossAmount) {
        this.grossAmount = grossAmount;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(Double netAmount) {
        this.netAmount = netAmount;
    }
}
