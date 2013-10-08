package no.knowit.camel.app;

import org.apache.camel.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HalloService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    public final List<String> sagtHalloTil = new ArrayList<String>();

    @Handler
    public void siHalloTil(String hvem) {
        sagtHalloTil.add(hvem);
        log.info("Hallo, {}!", hvem);
    }

}
