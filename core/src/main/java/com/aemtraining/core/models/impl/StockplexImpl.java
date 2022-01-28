//package com.aemtraining.core.models.impl;
//
//import com.aemtraining.core.models.Stockplex;
//import com.day.cq.wcm.api.Page;
//import com.day.cq.wcm.api.components.ComponentContext;
//import com.day.cq.wcm.api.designer.Style;
//import org.apache.sling.api.SlingHttpServletRequest;
//import org.apache.sling.api.resource.Resource;
//import org.apache.sling.models.annotations.DefaultInjectionStrategy;
//import org.apache.sling.models.annotations.Exporter;
//import org.apache.sling.models.annotations.Model;
//import org.apache.sling.models.annotations.injectorspecific.ResourcePath;
//import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
//import org.apache.sling.models.annotations.injectorspecific.Self;
//import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.Map;
//
//@Model(adaptables= SlingHttpServletRequest.class,
//        adapters= {Stockplex.class},
//        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL,
//        resourceType = StockplexImpl.RESOURCE_TYPE)
////the Exporter will format the json output of this component
//@Exporter(name="jackson", extensions = "json")
//public class StockplexImpl implements Stockplex {
//    protected static final String RESOURCE_TYPE = "wetrain/components/stockplex";
//    private static final Logger LOGGER = LoggerFactory.getLogger(StockplexImpl.class);
//    //Annotations to support data layer enablement and population
//    @Self
//    private SlingHttpServletRequest request;
//    @ScriptVariable
//    protected ComponentContext componentContext;
//
//    //HTL global objects in the model
//    //Learn more  at Helpx > HTL Global Objects
//    @ScriptVariable
//    private Page currentPage;
//    @ScriptVariable
//    private Style currentStyle;
//
//    //Properties on the current resource saved from the dialog of a component
//    @ValueMapValue
//    private String symbol;
//    @ValueMapValue
//    private String summary;
//
//    //content root of for stock data. /content/stocks
//    @ResourcePath(path = StockDataWriterJob.STOCK_IMPORT_FOLDER)
//    private Resource stocksRoot;
//
//    private double currentPrice;
//    private Map<String,Object> stockInfo;
//    @Override
//    public String getSymbol() {
//        return null;
//    }
//
//    @Override
//    public String getSummary() {
//        return null;
//    }
//
//    @Override
//    public String getShowStockInfo() {
//        return null;
//    }
//
//    @Override
//    public Double getCurrentPrice() {
//        return null;
//    }
//
//    @Override
//    public Map<String, Object> getStockInfo() {
//        return null;
//    }
//
//    @Override
//    public String getExportedType() {
//        return null;
//    }
//}
