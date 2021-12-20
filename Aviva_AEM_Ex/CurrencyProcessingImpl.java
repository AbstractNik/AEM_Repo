package com.adobe.core.servlets;

import java.io.IOException;
import java.util.*;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import com.google.gson.Gson;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component(service = { Servlet.class, CurrencyProcessing.class }, property = { Constants.SERVICE_DESCRIPTION
        + "=Servlet to process currencies for Aviva Products", "sling.servlet.methods=" + HttpConstants.METHOD_POST,
        "sling.servlet.paths=" + "/bin/currencyConversion" })
public class CurrencyProcessingImpl extends SlingAllMethodsServlet implements CurrencyProcessing {

    private static final Logger log = LoggerFactory.getLogger(CurrencyProcessingImpl.class);
    private static final  String RESOURCE_PATH="/etc/currencies/";
    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
            throws ServletException, IOException {
        super.doPost(request, response);
        ResourceResolver rr = null;
        try {
        log.info("-----Invoking Currency Conversion Sling Servlet-------------- ");

            //getting price value from the request
        double inputProductPrice = Double.parseDouble(request.getParameter("productPrice"));
        int temp = (int) (inputProductPrice * 100.0);
        double finalProductPrice = ((double) temp) / 100.0;
        log.info("Input Price of the product " + finalProductPrice);
        //getting currency list from the request
        String[] currencies = request.getParameterValues("currencies");
        String printInputCurrencies = Arrays.toString(currencies);
        log.info("Input Currencies of the products " + printInputCurrencies);

        // fetch resource resolver from the request
         rr = request.getResourceResolver();

        //creating Gson Object for exporting the resource as json
        Gson gson = new Gson();
        //fetch resource using the resource path
         Resource res = rr.getResource(RESOURCE_PATH);
        //fetching child resources 
        Iterator<Resource> resource = res.listChildren();
        Map<String,CurrencyBean> currencyBeanMapData=null;
        while (resource.hasNext()) {
            Resource childNode = resource.next();
            String currencyName = childNode.getValueMap().get("currencyName", String.class);
            double currencyConversionFactor= childNode.getValueMap().get("conversionFactor", Double.class);
            currencyBeanMapData =  fetchProcessedCurrencies(finalProductPrice, currencies, currencyName, currencyConversionFactor);
        }
        response.setContentType("application/json");
        String json = gson.toJson(currencyBeanMapData);
        response.getWriter().write(json);
       

          } catch (Exception e) {

              log.error(e.getMessage(), e);
            response.getWriter().write("Error while processing request");

        } finally {
            if (rr != null) {
                rr.close();
                
            }
        }

    }

    @Override
    public Map<String,CurrencyBean> fetchProcessedCurrencies(double finalProductPrice, String[] currencies,String currencyName,double currencyConversionFactor)
    {
        log.info("-----------Processing currency conversion-------------------------");
        Map<String,CurrencyBean> currencyBeanMap = new HashMap<String,CurrencyBean>();

        for (int clist = 0; clist < currencies.length; clist++) {
            if (currencies[clist].equalsIgnoreCase(currencyName)) {
                double convertedCurrencyValue = finalProductPrice * currencyConversionFactor;
                int tempCurrencyValue = (int) (convertedCurrencyValue * 100.0);
                double finalConvertedCurrencyValue = ((double) tempCurrencyValue) / 100.0;
                //currencyBeanMap.put(currencies[clist],new CurrencyBean(currencyName, finalConvertedCurrencyValue));
                currencyBeanMap.put(currencies[clist],new CurrencyBean(currencyName, finalConvertedCurrencyValue));

            }

        }

        return currencyBeanMap;
    }
                
        
        }


