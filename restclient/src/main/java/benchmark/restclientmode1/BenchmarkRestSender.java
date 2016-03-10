package benchmark.restclientmode1;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by ilya on 15.02.16.
 */
@FeignClient("restsender")
public interface BenchmarkRestSender {

    @RequestMapping(value = "/getfile", method = RequestMethod.GET, consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    byte[] getFile();

}
