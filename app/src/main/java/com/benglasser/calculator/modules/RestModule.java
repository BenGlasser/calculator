package com.benglasser.calculator.modules;

import com.benglasser.calculator.rest.CalculatorResource;
import com.google.inject.Provides;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.eclipse.jetty.servlet.DefaultServlet;

import javax.inject.Singleton;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import java.util.HashMap;

/**
 * Created by bglasser on 9/13/15.
 */

public class RestModule extends ServletModule{
    @Override
    protected void configureServlets() {
        bind(DefaultServlet.class).in(Singleton.class);

        bind(MessageBodyReader.class).to(JacksonJsonProvider.class);
        bind(MessageBodyWriter.class).to(JacksonJsonProvider.class);

        HashMap<String, String> options = new HashMap<>();
        options.put("com.sun.jersey.api.json.POJOMappingFeature", "true");
        serve("/*").with(GuiceContainer.class, options);
    }

    @Provides
    @Singleton
    public CalculatorResource getCalculatorResource() {
        return new CalculatorResource();
    }

}
