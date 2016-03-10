package benchmark.restsendermode2;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by ilya on 15.02.16.
 */
@FeignClient("restclient")
public interface IRestClient {

    @RequestMapping(value = "/sendfile", method = RequestMethod.PUT, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    void sendFile(byte[] fileAndStart);
}
