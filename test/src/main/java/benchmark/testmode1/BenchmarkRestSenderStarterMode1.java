package benchmark.testmode1;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by ilya on 16.02.16.
 */
@FeignClient("restsender")
public interface BenchmarkRestSenderStarterMode1 {
    @RequestMapping(value = "/start", method = RequestMethod.GET)
    void start(@RequestParam("filelength") int fileLength);
}
