package no.knowit.camel.routes;

import org.apache.camel.Consume;
import org.apache.camel.Exchange;
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
    }

    @Consume(uri = "direct:kast.IllegalArgumentException")
    public void kastIllegalArgumentException(Exchange exchange) {
        throw new IllegalArgumentException("Feiler exchange med id " + exchange.getExchangeId());
    }
}
