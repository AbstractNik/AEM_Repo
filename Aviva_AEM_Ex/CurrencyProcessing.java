package com.adobe.core.servlets;

import java.util.Map;

@FunctionalInterface
interface CurrencyProcessing {

 Map<String,CurrencyBean> fetchProcessedCurrencies(double finalProductPrice, String[] currencies, String currencyName, double currencyConversionFactor);

}
