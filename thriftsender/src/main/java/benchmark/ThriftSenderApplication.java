package benchmark;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * Created by ilya on 15.02.16.
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class ThriftSenderApplication {

    public static Logger failed;

    public static void main(String[] args) {
        failed = LoggerFactory.getLogger("FAILED_LOGGER");
        SpringApplication.run(ThriftSenderApplication.class, args);
    }
}
