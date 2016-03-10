package benchmark.thriftclientmode1;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import groovy.util.logging.Slf4j;
import info.developerblog.services.user.TBenchmarkService;
import info.developerblog.services.user.THandlerResponse;
import info.developerblog.spring.thrift.annotation.ThriftClient;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

/**
 * Created by ilya on 15.02.16.
 */
@Profile("mode1")
@Configuration
@Slf4j
@RestController
@EnableDiscoveryClient
@ManagedResource(objectName = "benchmark.thrift:name=Settings", description = "Settings")
public class AggregatorEndpoint {

    private static final MetricRegistry registry;

    private static final JmxReporter reporter;

    private static int counter;

    @Value("${initial.delay:200}")
    private int initialDelay;

    @Value("${is.initial.delay.random}")
    private String isInitialDelayRandom;

    static {
        registry = new MetricRegistry();
        reporter = JmxReporter.forRegistry(registry).inDomain("benchmark.thrift").build();
        reporter.start();
        counter = 0;
    }

    @ThriftClient(serviceId = "thriftsender", path = "/api")
    private TBenchmarkService.Client thriftSender;

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public void startBenchmark(@RequestParam("threadcount") int threadCount,
                               @RequestParam("duration") int duration) {
        Random random = new Random();

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(threadCount + 1);
        List<Future> futures = new ArrayList<>();

        //each new start creates new statistic
        String name = "get-file-" + counter++;

        //shuts up benchmark after duration
        executor.schedule(() -> {
            for (int i = 0; i < threadCount; i++) {
                futures.get(i).cancel(true);
            }

        }, duration, TimeUnit.SECONDS);

        //send get request to thrifthandler and update metric for get-file
        for (int i = 0; i < threadCount; i++) {
            futures.add(executor.submit(() -> {
                while (!Thread.currentThread().isInterrupted()) {

                    boolean isRandom = Boolean.parseBoolean(isInitialDelayRandom);
                    if ((isRandom && random.nextBoolean()) || !isRandom) {
                        try {
                            //for balance frequency
                            TimeUnit.MILLISECONDS.sleep(initialDelay);
                        } catch (InterruptedException e) {
                            return;
                        }
                    }

                    THandlerResponse response = null;
                    try {
                        response = thriftSender.getfile();
                    } catch (TException e) {
                        e.printStackTrace();
                    }

                    registry.timer(name).update(System.currentTimeMillis() - response.getStart(),
                            TimeUnit.MILLISECONDS);
                }
            }));
        }

    }

    @ManagedAttribute(description = "Initial Delay for thread sleep after received file")
    public int getInitialDelay() {
        return initialDelay;
    }

    @ManagedAttribute(description = "Set Initial Delay in milli seconds for thread sleep after received file")
    public void setInitialDelay(int initialDelay) {
        this.initialDelay = initialDelay;
    }

    @ManagedAttribute(description = "Is Initial Delay random")
    public String getInitialDelayRandom() {
        return isInitialDelayRandom;
    }

    @ManagedAttribute(description = "Set Initial Delay Random for initial Delay")
    public void setInitialDelayRandom(String initialDelayRandom) {
        isInitialDelayRandom = initialDelayRandom;
    }

}
