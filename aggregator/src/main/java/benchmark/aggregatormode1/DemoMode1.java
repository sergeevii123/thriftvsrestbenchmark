package benchmark.aggregatormode1;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.concurrent.TimeUnit;

/**
 * @author iisergeev ilya_sergeev@rgs.ru
 */
@Profile("mode1")
@Configuration
public class DemoMode1 {
    @Bean
    public CommandLineRunner demo(BenchmarkRestSenderStarterMode1 benchmarkRestSenderStarter,
                                  BenchmarkRestClientStarterMode1 benchmarkRestClientStarter,
                                  BenchmarkThriftSenderStarterMode1 benchmarkThriftSenderStarter,
                                  BenchmarkThriftClientStarterMode1 benchmarkThriftClientStarter) {
        return (args) -> {
            TimeUnit.SECONDS.sleep(15);

            for (int i = 0; i < 3; i++) {
                //start rest test
                benchmarkRestSenderStarter.start(4*1024*1024); //file size
                TimeUnit.SECONDS.sleep(2);
                benchmarkRestClientStarter.start(2, 30); //thread count and duration in seconds
                TimeUnit.SECONDS.sleep(32);

                //start thrift test
                benchmarkThriftSenderStarter.start(4*1024*1024); //file size
                TimeUnit.SECONDS.sleep(2);
                benchmarkThriftClientStarter.start(2, 30); //thread count and duration in seconds
                TimeUnit.SECONDS.sleep(32);
            }
            System.exit(0);
        };
    }
}
