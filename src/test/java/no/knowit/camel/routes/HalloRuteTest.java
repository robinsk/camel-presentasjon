package no.knowit.camel.routes;

import no.knowit.camel.app.HalloService;
import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringJUnit4ClassRunner;
import org.apache.camel.test.spring.MockEndpoints;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(CamelSpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:camel-presentasjon-context.xml")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@MockEndpoints
public class HalloRuteTest {

    @EndpointInject(uri = "mock:activemq:hallo")
    MockEndpoint hallokø;

    @Produce
    ProducerTemplate producer;

    @Autowired
    HalloService service;

    @Before
    public void before() {
        hallokø.setAssertPeriod(1000);
        hallokø.setResultWaitTime(3000);
    }

    @Test
    public void verifiser_at_det_blir_sagt_hallo_til_de_man_melder_om() throws InterruptedException {
        hallokø.setExpectedMessageCount(2);

        producer.sendBody("activemq:hallo", "Danskebåten");
        producer.sendBody("activemq:hallo", "Foredrag");

        Thread.sleep(1000);

        assertThat(service.sagtHalloTil, hasItems("Danskebåten", "Foredrag"));
    }
}
