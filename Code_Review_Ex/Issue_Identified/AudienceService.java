package com.aviva.aem.test;

import java.util.List;
import org.apache.sling.api.SlingHttpServletRequest;

//unused import
import com.adobe.acs.commons.wcm.datasources.DataSourceOption

@FunctionalInterface
public interface AudienceService {
/*Incorrect SlingHttpServletRequest Object , it has to be lower case E in Request */
    List<OPtion> getAudienceAsOptions(SlingHttpServletREquest request);


    
}
