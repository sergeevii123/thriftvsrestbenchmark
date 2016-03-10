package benchmark.thriftclientmode2;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import info.developerblog.services.user.TBenchmarkService;
import info.developerblog.services.user.TFileAndStart;
import info.developerblog.services.user.THandlerResponse;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import ru.trylogic.spring.boot.thrift.annotation.ThriftHandler;

import java.util.concurrent.TimeUnit;

/**
 * Created by ilya on 16.02.16.
 */
@Profile("mode2")
@ThriftHandler("/api")
public class ThriftBenchmarkClient implements TBenchmarkService.Iface {

    private static final MetricRegistry registry;

    private static final JmxReporter reporter;

    static {
        registry = new MetricRegistry();
        reporter = JmxReporter.forRegistry(registry).inDomain("benchmark.thrift").build();
        reporter.start();
    }

    @Autowired
    private AggregatorEndpoint aggregatorEndpoint;

    @Override
    public THandlerResponse getfile() throws TException {
        return null;
    }

    @Override
    public void sendFile(TFileAndStart t) throws TException {
        registry.timer(aggregatorEndpoint.name).update(System.currentTimeMillis() - t.getStart(),
                TimeUnit.MILLISECONDS);
    }
}
