package no.knowit.camel.routes;

import no.knowit.camel.app.HalloService;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HalloRute extends RouteBuilder {

    @Autowired
    HalloService service;

    @Override
    public void configure() throws Exception {
        from("activemq:hallo")
                .bean(service);
    }
}
