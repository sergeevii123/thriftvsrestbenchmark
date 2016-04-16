package benchmark.thriftsendermode2;

import info.developerblog.services.user.TBenchmarkService;
import info.developerblog.services.user.TFileAndStart;
import info.developerblog.spring.thrift.annotation.ThriftClient;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.EnableMBeanExport;
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

import static java.nio.ByteBuffer.wrap;
import static benchmark.ThriftSenderApplication.failed;

/**
 * Created by ilya on 15.02.16.
 */
@Profile("mode2")
@EnableMBeanExport
@RestController
@ManagedResource(objectName = "benchmark.thrift:name=Settings", description = "Settings")
public class TestEndpoint {

    private byte[] array;

    @Value("${initial.delay:200}")
    private int initialDelay;

    @Value("${is.initial.delay.random}")
    private String isInitialDelayRandom;

    @ThriftClient(serviceId = "thriftclient", path = "/api")
    private TBenchmarkService.Client client;

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public void startBenchmark(@RequestParam("threadcount") int threadCount,
                               @RequestParam("duration") int duration,
                               @RequestParam("filelength") int fileLength) {
        Random random = new Random();
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
                        client.sendFile(new TFileAndStart(wrap(array), System.currentTimeMillis()));
                    } catch (TException e) {
                        failed.error(Thread.currentThread().getName() + " error ", e);
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
