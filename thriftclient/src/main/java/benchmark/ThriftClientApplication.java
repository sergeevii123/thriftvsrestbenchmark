package benchmark;

import groovy.util.logging.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableMBeanExport;

/**
 * Created by ilya on 15.02.16.
 */
@Slf4j
@EnableFeignClients
@EnableDiscoveryClient
@EnableMBeanExport
@SpringBootApplication
public class ThriftClientApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ThriftClientApplication.class, args);
    }
}
