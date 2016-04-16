package benchmark.thriftsendermode1;

import info.developerblog.services.user.TBenchmarkService;
import info.developerblog.services.user.TFileAndStart;
import info.developerblog.services.user.THandlerResponse;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import ru.trylogic.spring.boot.thrift.annotation.ThriftHandler;

import static java.nio.ByteBuffer.wrap;

/**
 * Created by ilya on 16.02.16.
 */
@Profile("mode1")
@ThriftHandler("/api")
public class ThriftBenchmarkSender implements TBenchmarkService.Iface {

    @Autowired
    private TestEndpoint testEndpoint;

    @Override
    public THandlerResponse getfile() throws TException {
        return new THandlerResponse(wrap(testEndpoint.array), System.currentTimeMillis());
    }

    @Override
    public void sendFile(TFileAndStart t) throws TException {
        assert false;
    }
}
