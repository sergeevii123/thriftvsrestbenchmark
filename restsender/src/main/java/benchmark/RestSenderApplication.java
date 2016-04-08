package benchmark;

import groovy.util.logging.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by ilya on 09.02.16.
 */
@Slf4j
@EnableDiscoveryClient
@SpringBootApplication
public class RestSenderApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestSenderApplication.class, args);
    }
}
