package no.knowit.camel.routes;

import org.apache.camel.Consume;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MineFørsteRuter extends RouteBuilder {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void configure() throws Exception {
        from("activemq:a")
                .log(LoggingLevel.DEBUG, "no.knowit.camel.debug", "TRAFIKK PÅ KØ A: ${body}")
                .to("activemq:b");

        from("direct:a")
                .to("direct:b");
    }

    @Consume(uri = "direct:b")
    public void konsumerDirectB(String message) {
        log.info("TRAFIKK PÅ DIREKTE-B: {}", message);
    }
}
