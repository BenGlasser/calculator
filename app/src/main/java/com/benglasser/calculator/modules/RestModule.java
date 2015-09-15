package com.benglasser.calculator.modules;

import java.util.HashMap;
import java.util.List;

import javax.inject.Singleton;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;

import lombok.RequiredArgsConstructor;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.eclipse.jetty.servlet.DefaultServlet;

import com.benglasser.calculator.operations.Operation;
import com.benglasser.calculator.rest.CalculatorResource;
import com.google.inject.Provides;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

/**
 * Created by bglasser on 9/13/15.
 */

@RequiredArgsConstructor
public class RestModule extends ServletModule{
    private final List<Operation> operations;

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
        return new CalculatorResource(operations);
    }

}
