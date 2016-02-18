package benchmark;

import groovy.util.logging.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * Created by ilya on 15.02.16.
 */
@Slf4j
@EnableFeignClients
//@EnableDiscoveryClient // FOR TEST
@SpringBootApplication
public class RestHandlerApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestHandlerApplication.class, args);
    }
}
