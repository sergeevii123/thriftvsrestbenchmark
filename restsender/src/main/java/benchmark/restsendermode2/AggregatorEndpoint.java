package benchmark.restsendermode2;

import benchmark.FileAndStart;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Profile;
import org.springframework.jmx.export.annotation.ManagedAttribute;
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

/**
 * Created by ilya on 15.02.16.
 */
@Profile("mode2")
@Configuration
@EnableFeignClients
@EnableMBeanExport
@RestController
public class AggregatorEndpoint {

    private byte[] array;

    @Value("${initial.delay:200}")
    private int initialDelay;

    @Value("${is.initial.delay.random}")
    private String isInitialDelayRandom;

    @Autowired
    private IRestClient client;

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public void startBenchmark(@RequestParam("threadcount") int threadCount,
                               @RequestParam("duration") int duration,
                               @RequestParam("filelength") int fileLength) {
        Random random = new Random();
        ObjectMapper mapper = new ObjectMapper();
        array = new byte[fileLength];
        random.nextBytes(array);

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(threadCount + 1);
        List<Future> futures = new ArrayList<>();

        //shuts up benchmark after duration
        executor.schedule(() -> {
            for (int i = 0; i < threadCount; i++) {
                futures.get(i).cancel(true);
            }
        }, duration, TimeUnit.SECONDS);


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

                    try {
                        client.sendFile(mapper.writeValueAsBytes(new FileAndStart(System.currentTimeMillis(), array)));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }

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
