package benchmark.testmode1;

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
@Profile("mode1")
@Configuration
public class TestMode1 {

    private int threadCount;
    private int fileLength;
    private int duration;

    @Autowired
    private ConfigurableApplicationContext context;

    @Bean
    public CommandLineRunner demo(BenchmarkRestSenderStarterMode1 benchmarkRestSenderStarter,
                                  BenchmarkRestClientStarterMode1 benchmarkRestClientStarter,
                                  BenchmarkThriftSenderStarterMode1 benchmarkThriftSenderStarter,
                                  BenchmarkThriftClientStarterMode1 benchmarkThriftClientStarter) {
        return (args) -> {
            TimeUnit.SECONDS.sleep(15);

            //start rest test
            benchmarkRestSenderStarter.start(fileLength);
            TimeUnit.SECONDS.sleep(2);
            benchmarkRestClientStarter.start(threadCount, duration, fileLength);
            TimeUnit.SECONDS.sleep(duration + 2);

            //start thrift test
            benchmarkThriftSenderStarter.start(fileLength);
            TimeUnit.SECONDS.sleep(2);
            benchmarkThriftClientStarter.start(threadCount, duration, fileLength);
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
