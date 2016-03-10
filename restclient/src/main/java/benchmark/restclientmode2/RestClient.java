package benchmark.restclientmode2;

import benchmark.FileAndStart;
import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by ilya on 16.02.16.
 */
@Profile("mode2")
@RestController
public class RestClient {

    private static final MetricRegistry registry;

    private static final JmxReporter reporter;

    private static int counter;

    private static String name;

    private static ObjectMapper mapper;

    static {
        registry = new MetricRegistry();
        reporter = JmxReporter.forRegistry(registry).inDomain("benchmark.rest").build();
        reporter.start();
        mapper = new ObjectMapper();
    }

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public void start() {
        //each new start creates new statistic
        name = "get-file-" + counter++;
    }

    @RequestMapping(value = "/sendfile", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void getFile(HttpServletRequest httpServletRequest) throws IOException {
        FileAndStart fileAndStart = mapper.readValue(httpServletRequest.getInputStream(), FileAndStart.class);
        registry.timer(name).update(System.currentTimeMillis() - fileAndStart.getStart(),
                TimeUnit.MILLISECONDS);
    }

}
