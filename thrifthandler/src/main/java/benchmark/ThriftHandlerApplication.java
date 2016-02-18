package benchmark;

import groovy.util.logging.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * Created by ilya on 15.02.16.
 */
@Slf4j
@EnableFeignClients
//@EnableDiscoveryClient //FOR TEST
@SpringBootApplication
public class ThriftHandlerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ThriftHandlerApplication.class, args);
    }
}