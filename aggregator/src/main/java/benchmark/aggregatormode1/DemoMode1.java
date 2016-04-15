package benchmark.aggregatormode1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;
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

    @Autowired
    private ConfigurableApplicationContext context;

    @Bean
    public CommandLineRunner demo(BenchmarkRestSenderStarterMode1 benchmarkRestSenderStarter,
                                  BenchmarkRestClientStarterMode1 benchmarkRestClientStarter,
                                  BenchmarkThriftSenderStarterMode1 benchmarkThriftSenderStarter,
                                  BenchmarkThriftClientStarterMode1 benchmarkThriftClientStarter) {
        return (args) -> {
            TimeUnit.SECONDS.sleep(15);

            for (int i = 0; i < 1; i++) {
                //start rest test
                benchmarkRestSenderStarter.start(2*1024*1024); //file size
                TimeUnit.SECONDS.sleep(2);
                benchmarkRestClientStarter.start(2, 30); //thread count and duration in seconds
                TimeUnit.SECONDS.sleep(32);

                //start thrift test
                benchmarkThriftSenderStarter.start(2*1024*1024); //file size
                TimeUnit.SECONDS.sleep(2);
                benchmarkThriftClientStarter.start(2, 30); //thread count and duration in seconds
                TimeUnit.SECONDS.sleep(32);
            }

            context.close();
            System.exit(0);
        };
    }
}
