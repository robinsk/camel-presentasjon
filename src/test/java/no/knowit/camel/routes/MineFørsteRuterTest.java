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
public class MineFørsteRuterTest {

    @EndpointInject(uri = "mock:activemq:b")
    MockEndpoint køB;

    @EndpointInject(uri = "mock:direct:b")
    MockEndpoint direkteB;

    @Produce
    ProducerTemplate producer;

    @Before
    public void before() {
        køB.setAssertPeriod(1000);
        køB.setResultWaitTime(3000);
        køB.reset();
    }

    @Test
    public void send_til_kø_a_skal_videresende_til_kø_b() throws InterruptedException {
        køB.expectedBodiesReceived("omforladels");

        producer.sendBody("activemq:a", "omforladels");

        køB.assertIsSatisfied();
    }

    @Test
    public void send_fem_meldinger_til_kø_a_skal_gi_fem_meldinger_til_kø_b() throws InterruptedException {
        køB.expectedBodiesReceived("message1", "message2", "message3", "message4", "message5");

        producer.sendBody("activemq:a", "message1");
        producer.sendBody("activemq:a", "message2");
        producer.sendBody("activemq:a", "message3");
        producer.sendBody("activemq:a", "message4");
        producer.sendBody("activemq:a", "message5");

        køB.assertIsSatisfied();
    }

    @Test
    public void funker_det_med_directs_med_samme_uri_mon_tro() throws InterruptedException {
        direkteB.expectedBodiesReceived("testing av test");

        producer.sendBody("direct:a", "testing av test");

        direkteB.assertIsSatisfied();
    }

}
