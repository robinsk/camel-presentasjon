package no.knowit.camel.routes;

import org.apache.camel.Consume;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class KøMedFeilhåndtering extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("activemq:kø.som.kaster.IllegalArgumentException").routeId("feilhåndtering 1")
                .to("direct:kast.IllegalArgumentException");

        from("activemq:kø.som.kaster.IllegalArgumentException.med.onException").routeId("feilhåndtering 2")
                .onException(IllegalArgumentException.class)
                    .to("activemq:errors.IllegalArgumentException")
                    .end()
                .to("direct:kast.IllegalArgumentException");

        from("activemq:kø.med.throwException").routeId("feilhåndtering 3")
                .throwException(new RuntimeException("this is so wrong"));

        from("activemq:kø.med.kasting.av.exception.i.en.processor").routeId("feilhåndtering 4")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        throw new IllegalStateException("ugyldig melding, gitt");
                    }
                });

        from("activemq:kø.med.kasting.av.exception.i.onException").routeId("feilhåndtering 5")
                .onException(IllegalArgumentException.class)
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        throw new IllegalStateException(exchange.getException());
                    }
                })
                .end()
                .to("direct:kast.IllegalArgumentException");

    }

    @Consume(uri = "direct:kast.IllegalArgumentException")
    public void kastIllegalArgumentException(Exchange exchange) {
        throw new IllegalArgumentException("Feiler exchange med id " + exchange.getExchangeId());
    }
}
