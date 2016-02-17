package benchmark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Scanner;

/**
 * Created by ilya on 15.02.16.
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class Aggregator {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Aggregator.class, args);
        /*Scanner sc = new Scanner(System.in);

        if (sc.next().equalsIgnoreCase("start rest")) {*/
            context.getBean(BenchmarkRestHandlerStarter.class).start(10000);
            context.getBean(BenchmarkRestClientStarter.class).start(2, 60);
        //}
    }
}
