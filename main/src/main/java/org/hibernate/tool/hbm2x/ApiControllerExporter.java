package org.hibernate.tool.hbm2x;

import java.util.Map;

import org.hibernate.tool.hbm2x.pojo.POJOClass;

public class ApiControllerExporter extends POJOExporter {

    private static final String API_API_FTL = "api/controller.ftl";

    private String sessionFactoryName = "SessionFactory";

    public ApiControllerExporter() {
    		super();
    }
    
    protected void init() {
    	super.init();
    	setTemplateName(API_API_FTL);
    	setFilePattern("{package-name}/{class-name}Controller.java");    	    	
    }
    
    protected void exportComponent(Map<String, Object> additionalContext, POJOClass element) {
    	// noop - we dont want components
    }

	public String getSessionFactoryName() {
		return sessionFactoryName;
	}

	public void setSessionFactoryName(String sessionFactoryName) {
		this.sessionFactoryName = sessionFactoryName;
	}

	protected void setupContext() {
		getProperties().put("sessionFactoryName", getSessionFactoryName());
		super.setupContext();		
	}
	
	public String getName() {
		return "hbm2dao";
	}

	
}
