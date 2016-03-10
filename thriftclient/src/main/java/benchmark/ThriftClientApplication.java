package benchmark;

import groovy.util.logging.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.EnableMBeanExport;

/**
 * Created by ilya on 15.02.16.
 */
@Slf4j
@EnableFeignClients
@EnableMBeanExport
@SpringBootApplication
public class ThriftClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ThriftClientApplication.class, args);
    }
}
