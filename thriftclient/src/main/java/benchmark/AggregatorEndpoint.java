package benchmark;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import groovy.util.logging.Slf4j;
import info.developerblog.services.user.TBenchmarkService;
import info.developerblog.services.user.THandlerResponse;
import info.developerblog.spring.thrift.annotation.ThriftClient;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by ilya on 15.02.16.
 */
@Slf4j
@RestController
public class AggregatorEndpoint {

    private static final MetricRegistry registry;

    private static final JmxReporter reporter;

    private static int counter;

    static {
        registry = new MetricRegistry();
        reporter = JmxReporter.forRegistry(registry).inDomain("benchmark.thrift").build();
        reporter.start();
    }

    @ThriftClient(serviceId = "thrifthandler", path = "/api")
    private TBenchmarkService.Client thriftHandler;

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public void startBenchmark(@RequestParam("threadcount") int threadCount,
                               @RequestParam("duration") int duration) {
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
                    THandlerResponse response = null;
                    try {
                        response = thriftHandler.getfile();
                    } catch (TException e) {
                        e.printStackTrace();
                    }

                    registry.timer(name).update(System.currentTimeMillis() - response.getStart(),
                            TimeUnit.MILLISECONDS);
                }
            }));
        }

    }

}
