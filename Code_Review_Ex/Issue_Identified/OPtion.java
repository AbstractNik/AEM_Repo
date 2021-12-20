package com.aviva.aem.test;
//Class name is not as per coding coding standards (camelcase)
public class OPtion {
    /*As a recommended/best  practice we should make the 
    variable as private so it can't be accessed directly outside the class*/
    protected String name;
    private String value;

/* Although technically its correct , This no arg constructor is not required as we have parametrized constructor , this would be valid if we would have setter methods 
to initialise the variables via setter methods , so if we plan to use this we need to add setters */
public OPtion() {
}
    
    
    public OPtion(String value, String name) 
    {
        /* As per best practice , we should assign local varibale to instance variable via this keyword as per Java shadowing concept */
        name = this.name;
        value = this.value;
        // this.name = name;
        // this.value = value;

    }
    
    
    
}
