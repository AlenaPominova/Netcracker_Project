package com.site;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.site.config.SecurityConfig;
import com.site.config.WebConfig;
import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.AccessedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class AppInit implements WebApplicationInitializer {
    public final static String DISPATCHER = "dispatcher";

    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(WebConfig.class);
        context.register(SecurityConfig.class);
        servletContext.addListener(new ContextLoaderListener(context));

        DispatcherServlet ds = new DispatcherServlet(context);
        ds.setThrowExceptionIfNoHandlerFound(true);

        ServletRegistration.Dynamic servlet = servletContext.addServlet(DISPATCHER, ds);
        servlet.addMapping("/");
        servlet.setLoadOnStartup(1);

        System.setProperty("hazelcast.jcache.provider.type", "server");
        // Create the JCache CacheManager
        CacheManager manager = Caching.getCachingProvider().getCacheManager();
        MutableConfiguration<String, String> configuration = new MutableConfiguration<String, String>();
        // Expire entries after 1 minute
        configuration.setExpiryPolicyFactory(AccessedExpiryPolicy.factoryOf(Duration.ONE_MINUTE));
        // Get a Cache called "myCache" and configure with 1 minute expiry
        Cache<String, String> myCache = manager.createCache("myCache", configuration);
        // Put and Get a value from "myCache"
        myCache.put("key", "value");
        String value = myCache.get("key");
        System.out.println(value);
    }
}