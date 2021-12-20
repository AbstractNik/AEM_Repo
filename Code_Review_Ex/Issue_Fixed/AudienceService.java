package com.aviva.aem.test;

import java.util.List;
import org.apache.sling.api.SlingHttpServletRequest;


@FunctionalInterface
public interface AudienceService {

    List<OPtion> getAudienceAsOptions(SlingHttpServletRequest request);


}