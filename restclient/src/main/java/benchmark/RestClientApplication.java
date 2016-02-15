package benchmark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * Created by ilya on 09.02.16.
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class RestClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestClientApplication.class, args);
    }
}
