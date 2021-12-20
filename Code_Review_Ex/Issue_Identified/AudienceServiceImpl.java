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

//need to add metatype = true to the attribute to @Component annotation
@Component(label="Audience Service" , description="Get Configurations", immediate=true)
@Service(AudienceService.class)
@Properties({@Property(name="rootPath",label="Root Path", value=DEFAULT_PATH)})

public class AudienceServiceImpl implements AudienceService {
    
    
// it is best practice to make constant as private so it cant be accessed from outside the class
   public  static final String DEFAULT_PATH="etc/default";
   private String rootPath=DEFAULT_PATH;
  
  //list should have a Generic Type  of OPtion class instead of using RAW collection
  public List getAudienceAsOptions(SlingHttpServletRequest request){
  
  //list should have a Generic Type of OPtion class instead of using RAW collection
  List list = new ArrayList<>();
  Resource resource = request.getResourceResolver().getResource(rootPath);
   
   if (resource!=null){
   //Type of List should be String instead of String Array
   List<String[]> audienceList = getAudiences(resource);
   // audienceList should contain String objects instead String array
    for (String audienceName: audienceList){
	//Class name mistmatch it should be renamed to OPtion
	list.add(new Option(audienceName,audienceName));
	}
   
   }
   return list;
  }
  
  private static List<String> getAudiences(Resource resource){
  // interface List instance has to be instantiated using ArrayList class 
  List<String> list = new List<>();
  Iterator<Resource> childNodes= resource.listChildren();
   while(childNodes.hasNext()){
     //add method should contain String Objects instead of String array 
   // it is best practice to use value map to read resource properties
  list.add(new String[]{childNodes.next().getTitle(), childNodes.next().getName()});
   
   }
    return list;
  
  }
  
  @Activate
  //it is a best practice suggested by Adobe to define activate method of  protected access level instead of public access level
  public void activate(Map<String,Object> properties){
  
  rootPath= PropertiesUtil.toString(properties.get("rootPath"),null);
  
  }

}


    

