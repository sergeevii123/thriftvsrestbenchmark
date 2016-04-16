package benchmark.testmode2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.concurrent.TimeUnit;

/**
 * @author iisergeev ilya_sergeev@rgs.ru
 */
@Profile("mode2")
@Configuration
public class TestMode2 {

    private int threadCount;
    private int fileLength;
    private int duration;

    @Autowired
    private ConfigurableApplicationContext context;

    @Bean
    public CommandLineRunner demo(BenchmarkRestSenderStarterMode2 benchmarkRestSenderStarter,
                                  BenchmarkRestClientStarterMode2 benchmarkRestClientStarter,
                                  BenchmarkThriftSenderStarterMode2 benchmarkThriftSenderStarter,
                                  BenchmarkThriftClientStarterMode2 benchmarkThriftClientStarter) {
        return (args) -> {
            TimeUnit.SECONDS.sleep(15);

            //start rest test
            benchmarkRestClientStarter.start(threadCount, duration, fileLength);
            TimeUnit.SECONDS.sleep(2);
            benchmarkRestSenderStarter.start(threadCount, duration, fileLength);
            TimeUnit.SECONDS.sleep(duration + 2);

            //start thrift test
            benchmarkThriftClientStarter.start(threadCount, duration, fileLength);
            TimeUnit.SECONDS.sleep(2);
            benchmarkThriftSenderStarter.start(threadCount, duration, fileLength);
            TimeUnit.SECONDS.sleep(duration + 2);


            context.close();
            System.exit(0);
        };
    }

    @Value("${thread.count}")
    public void setThreadCount(String threadCount) {
        this.threadCount = Integer.parseInt(threadCount);
    }

    @Value("${file.length}")
    public void setFileLength(String fileLength) {
        this.fileLength = Integer.parseInt(fileLength);
    }

    @Value("${duration}")
    public void setDuration(String duration) {
        this.duration = Integer.parseInt(duration);
    }
}
