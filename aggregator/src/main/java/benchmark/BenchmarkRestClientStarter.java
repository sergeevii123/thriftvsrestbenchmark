package benchmark;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by ilya on 15.02.16.
 */
@FeignClient("restclient")
public interface BenchmarkRestClientStarter {
    @RequestMapping(value = "/start", method = RequestMethod.GET)
    void start(@RequestParam("threadcount") int threadCount,
               @RequestParam("duration") int duration);
}
