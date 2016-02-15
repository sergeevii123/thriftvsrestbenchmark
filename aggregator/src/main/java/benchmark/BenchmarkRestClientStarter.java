package benchmark;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by ilya on 15.02.16.
 */
@FeignClient("benchmark-rest-client")
public interface BenchmarkRestClientStarter {
    @RequestMapping(value = "/start", method = RequestMethod.GET)
    void start(@PathVariable("threadcount") int threadCount,
               @PathVariable("duration") int duration);
}
