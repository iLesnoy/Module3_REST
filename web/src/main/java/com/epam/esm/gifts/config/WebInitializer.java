package com.epam.esm.gifts.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletRegistration;

public class WebInitializer implements WebApplicationInitializer {


    private static final String CONFIG_LOCATION = "com.epam.esm.gifts.config";
    private static final String DISPATCHER_SERVLET_NAME = "dispatcher";
    private static final String DISPATCHER_MAPPING = "/";
    private static final int DISPATCHER_LOAD_ON_STARTUP = 1;

    public void onStartup(javax.servlet.ServletContext servletContext) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.setConfigLocations(CONFIG_LOCATION);
        context.register(WebConfig.class);
        servletContext.addListener(new ContextLoaderListener(context));
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet(DISPATCHER_SERVLET_NAME, new DispatcherServlet(context));
        dispatcher.setLoadOnStartup(DISPATCHER_LOAD_ON_STARTUP);
        dispatcher.addMapping(DISPATCHER_MAPPING);
        dispatcher.setInitParameter("enableLoggingRequestDetails", "true");
        dispatcher.setInitParameter("throwExceptionIfNoHandlerFound", "true");
    }
}