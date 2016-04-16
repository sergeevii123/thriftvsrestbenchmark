package benchmark;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by ilya on 09.02.16.
 */
@EnableDiscoveryClient
@SpringBootApplication
public class RestSenderApplication {

    public static Logger failed;

    public static void main(String[] args) {
        failed = LoggerFactory.getLogger("FAILED_LOGGER");
        SpringApplication.run(RestSenderApplication.class, args);
    }
}
