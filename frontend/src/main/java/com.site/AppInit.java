package com.site;

import com.site.config.WebConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class AppInit implements WebApplicationInitializer {
    public final static String DISPATCHER = "dispatcher";

    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(WebConfig.class);
        servletContext.addListener(new ContextLoaderListener(context));

        DispatcherServlet ds = new DispatcherServlet(context);
        ds.setThrowExceptionIfNoHandlerFound(true);

        ServletRegistration.Dynamic servlet = servletContext.addServlet(DISPATCHER, ds);
        servlet.addMapping("/");
        servlet.setLoadOnStartup(1);
    }
}