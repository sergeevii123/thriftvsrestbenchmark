package benchmark;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
@RestController
public class AggregatorEndpoint {

    private static final MetricRegistry registry;

    private static final JmxReporter reporter;

    static {
        registry = new MetricRegistry();
        reporter = JmxReporter.forRegistry(registry).inDomain("benchmark.rest").build();
        reporter.start();
    }

    @Autowired
    private BenchmarkRestHandler restHandler;

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public void startBenchmark(@PathVariable("threadcount") int threadCount,
                               @PathVariable("duration") int duration) {

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(threadCount + 1);
        List<Future> futures = new ArrayList<>();
        //send get request to resthandler and update metric for get-file
        for (int i = 0; i < threadCount; i++) {
            futures.add(executor.submit(() -> {
                while (!Thread.interrupted()) {
                    BenchmarkRestHandler.RestHandlerResponse response = restHandler.getFile();
                    registry.timer("get-file").update(System.currentTimeMillis() - response.getCreated(),
                            TimeUnit.MILLISECONDS);
                }
            }));
        }
        //shuts up benchmark after duration
        executor.schedule(() -> {
            for (int i = 0; i < threadCount; i++) {
                futures.get(i).cancel(true);
            }
        }, duration, TimeUnit.SECONDS);

    }

}
