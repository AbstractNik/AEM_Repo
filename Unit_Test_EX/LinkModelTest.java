package com.adobe.core.models;

import com.day.cq.wcm.api.Page;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith({AemContextExtension.class, MockitoExtension.class})
class LinkModelTest {

    private final AemContext aemContext= new AemContext();
    private static final String HTML_EXTENSION = ". html";

    @BeforeEach
    void setUp() {
        //we can load as many json as per the unit test requirement to mock required resources
        aemContext.addModelsForClasses(LinkModel.class);
       aemContext.load().json("com/adobe/core/models/LinkModelDummy.json","/content");
        aemContext.load().json("com/adobe/core/models/DummyPage.json","/content");
    }

    @Test
    void getLink() {
        Resource resource= aemContext.currentResource("/content/linkmodel");
        Page containingPage= aemContext.pageManager().getContainingPage(resource.getPath());
        LinkModel linkModel= aemContext.registerService(new LinkModel());
      final String expectedPath= "ExpectedValue";
      if(containingPage!=null) {
          Assertions.assertEquals(expectedPath + HTML_EXTENSION, linkModel.getLink() + HTML_EXTENSION);
      }
    }

    @Test
    void getTitle() {

        //unit test to check if page object is null , we should get the resource/component title
        Resource resource= aemContext.currentResource("/content/linkmodel");
        Page containingPage= aemContext.pageManager().getContainingPage(resource.getPath());
        LinkModel linkModel= aemContext.registerService(new LinkModel());
        final String expectedComponentTitle="ExpectedValue";
        if(containingPage==null) {
            Assertions.assertEquals(expectedComponentTitle, linkModel.getTitle());
        }

    }

    @Test
    void isNavigationPageTitleNotNull(){
        //unit test to check if navigation title of the current page is not null
        Page currentPage= aemContext.currentResource("/content/nonblanknavtitle").adaptTo(Page.class);
        //we can also get mock page via aemContext.currentPage();
        String navigationTitle= currentPage.getNavigationTitle();
        LinkModel linkModel= aemContext.registerService(new LinkModel());
        final String expectedPageNavTitle="ExpectedValue";
        if(navigationTitle!=null) {
            Assertions.assertEquals(expectedPageNavTitle, linkModel.getTitle());
        }

    }

    @Test
    void isNavigationPageTitleNull(){
        //unit test to check if navigation title of the current page is null
        Page currentPage= aemContext.currentResource("/content/blanknavtitle").adaptTo(Page.class);
       // we can also get mock page via aemContext.currentPage();
        String navigationTitle= currentPage.getNavigationTitle();
        LinkModel linkModel= aemContext.registerService(new LinkModel());
        final String expectedPageTitle="ExpectedValue";
        if(navigationTitle==null) {
            Assertions.assertEquals(expectedPageTitle, linkModel.getTitle());
        }

    }
}