package com.adobe.core.servlets;

public class CurrencyBean {

    private String currencyName;
    private double price;

    public CurrencyBean(String currencyName, double price) {
        this.currencyName = currencyName;
        this.price = price;
    }
    
    public String getCurrencyName() {
        return currencyName;
    }
    
    public double getPrice() {
        return price;
    }
    @Override
    public String toString() {
        return "CurrencyBean [currencyName=" + currencyName + ", price=" + price + "]";
    }
    


    
}
