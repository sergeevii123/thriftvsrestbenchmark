package benchmark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.TimeUnit;

/**
 * Created by ilya on 15.02.16.
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class Aggregator {

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(Aggregator.class, args);
        //timeout for discovery
        TimeUnit.SECONDS.sleep(5);

        for (int i = 0; i < 3; i++) {
            //start rest test
            context.getBean(BenchmarkRestHandlerStarter.class).start(4*1024*1024); //file size
            TimeUnit.SECONDS.sleep(2);
            context.getBean(BenchmarkRestClientStarter.class).start(2, 30); //thread count and duration in seconds
            TimeUnit.SECONDS.sleep(32);

            //start thrift test
            context.getBean(BenchmarkThriftHandlerStarter.class).start(4*1024*1024); //file size
            TimeUnit.SECONDS.sleep(2);
            context.getBean(BenchmarkThriftClientStarter.class).start(2, 30); //thread count and duration in seconds
            TimeUnit.SECONDS.sleep(32);
        }


        System.exit(0);
    }
}
