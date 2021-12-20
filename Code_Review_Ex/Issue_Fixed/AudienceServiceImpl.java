package com.aviva.aem.test;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.felix.scr.annotations.Activate
import org.apache.felix.scr.annotations.Component
import org.apache.felix.scr.annotations.Properties
import org.apache.felix.scr.annotations.Property
import org.apache.felix.scr.annotations.Service
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.commons.osgi.PropertiesUtil;



@Component(label="Audience Service" , description="Get Configurations", immediate=true,metatype=true)
@Service(AudienceService.class)
@Properties({@Property(name="rootPath",label="Root Path", value=DEFAULT_PATH)})

public class AudienceServiceImpl implements AudienceService {
    
    

   private  static final String DEFAULT_PATH="etc/default";
   private String rootPath=DEFAULT_PATH;
  

  public List<OPtion> getAudienceAsOptions(SlingHttpServletRequest request){
  

  List<OPtion> list = new ArrayList<OPtion>();
  Resource resource = request.getResourceResolver().getResource(rootPath);
   
   if (resource!=null){

   List<String> audienceList = getAudiences(resource);
   
    for (String audienceName: audienceList){
	
	list.add(new OPtion(audienceName,audienceName));
	}
   
   }
   return list;
  }
  
  private static List<String> getAudiences(Resource resource){
 
  List<String> list = new ArrayList<String>();
  Iterator<Resource> childNodes= resource.listChildren();
   while(childNodes.hasNext()){
   
  list.add(childNodes.next().getName());
   
   }
    return list;
  
  }
  
  @Activate
  protected void activate(Map<String,Object> properties){
  
  rootPath= PropertiesUtil.toString(properties.get("rootPath"),null);
  
  }

}


    

