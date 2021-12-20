package com.adobe.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class LinkModel {

    private static final String HTML_EXTENSION = ". html";

    @Inject
    private String title;

    private String link;
     
    @Inject
    private Resource resource;

    @Inject
    private PageManager pageManager;


    @PostConstruct
    public void init() {
        appendExtensionToLink();
    }
    
    public String getLink() {
        return link;
    }

    public String getTitle() {
        Page page = pageManager.getContainingPage(link);

        if (page == null) {
            return title;
        }

        return getPageTitle(page);
    }

    private String getPageTitle(Page page) {
        String navigationTitle = page.getNavigationTitle();

        if (navigationTitle != null) {

            return navigationTitle;

        }

        return page.getTitle();
    }
    
    private void appendExtensionToLink() {
        link = resource.getPath();
        Page page = pageManager.getContainingPage(link);
        if (page != null) {
            link = link + HTML_EXTENSION;
        }
    }
    
}
