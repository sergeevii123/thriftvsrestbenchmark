package benchmark.aggregatormode2;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by ilya on 15.02.16.
 */
@FeignClient("restclient")
public interface BenchmarkRestClientStarterMode2 {
    @RequestMapping(value = "/start", method = RequestMethod.GET)
    void start();
}
