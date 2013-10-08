package no.knowit.camel.routes;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringJUnit4ClassRunner;
import org.apache.camel.test.spring.MockEndpoints;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

@RunWith(CamelSpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:camel-presentasjon-context.xml")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@MockEndpoints
public class KøMedFeilhåndteringTest {

    @EndpointInject(uri = "mock:activemq:deadletter")
    MockEndpoint feilkø;

    @EndpointInject(uri = "mock:activemq:errors.IllegalArgumentException")
    MockEndpoint illargFeilkø;

    @Produce
    ProducerTemplate producer;

    @Before
    public void before() {
        feilkø.setAssertPeriod(1000);
        feilkø.setResultWaitTime(3000);

        illargFeilkø.setAssertPeriod(1000);
        illargFeilkø.setResultWaitTime(3000);
    }

    @Test
    public void verifiser_at_melding_havner_på_feilkø() throws InterruptedException {
        feilkø.expectedBodiesReceived("viktig melding");

        producer.sendBody("activemq:kø.som.kaster.IllegalArgumentException", "viktig melding");

        feilkø.assertIsSatisfied();
    }

    @Test
    public void kø_med_onException_som_sender_til_egen_kø_skal_ikke_sende_til_global_feilkø() throws InterruptedException {
        illargFeilkø.expectedBodiesReceived("tryne på stacken");
        feilkø.expectedMessageCount(0);

        producer.sendBody("activemq:kø.som.kaster.IllegalArgumentException.med.onException", "tryne på stacken");

        feilkø.assertIsSatisfied();
        illargFeilkø.assertIsSatisfied();
    }
}
