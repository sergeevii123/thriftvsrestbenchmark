package benchmark.aggregatormode2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.concurrent.TimeUnit;

/**
 * @author iisergeev ilya_sergeev@rgs.ru
 */
@Profile("mode2")
@Configuration
public class DemoMode2 {
    @Bean
    public CommandLineRunner demo(BenchmarkRestSenderStarterMode2 benchmarkRestSenderStarter,
                                  BenchmarkRestClientStarterMode2 benchmarkRestClientStarter,
                                  BenchmarkThriftSenderStarterMode2 benchmarkThriftSenderStarter,
                                  BenchmarkThriftClientStarterMode2 benchmarkThriftClientStarter) {
        return (args) -> {
            TimeUnit.SECONDS.sleep(15);

            for (int i = 0; i < 3; i++) {
                //start rest test
                benchmarkRestClientStarter.start();
                TimeUnit.SECONDS.sleep(2);
                benchmarkRestSenderStarter.start(5, 30, 2*1024*1024); //thread count, duration in seconds, file size
                TimeUnit.SECONDS.sleep(32);

                //start thrift test
                benchmarkThriftClientStarter.start();
                TimeUnit.SECONDS.sleep(2);
                benchmarkThriftSenderStarter.start(5, 30, 2*1024*1024); //thread count, duration in seconds, file size
                TimeUnit.SECONDS.sleep(32);
            }
            System.exit(0);
        };
    }
}
