package benchmark;

import info.developerblog.services.user.TBenchmarkService;
import info.developerblog.services.user.THandlerResponse;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.trylogic.spring.boot.thrift.annotation.ThriftHandler;

import java.util.Random;

import static java.nio.ByteBuffer.wrap;

/**
 * Created by ilya on 16.02.16.
 */
@ru.trylogic.spring.boot.thrift.annotation.ThriftHandler("/api")
public class ThriftBenchmarkHandler implements TBenchmarkService.Iface {

    @Autowired
    private AggregatorEndpoint aggregatorEndpoint;

    @Override
    public THandlerResponse getfile() throws TException {
        return new THandlerResponse(wrap(aggregatorEndpoint.array), System.currentTimeMillis());
    }
}
