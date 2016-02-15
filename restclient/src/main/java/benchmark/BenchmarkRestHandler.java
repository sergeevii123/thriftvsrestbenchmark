package benchmark;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by ilya on 15.02.16.
 */
@FeignClient("benchmark-rest-handler")
public interface BenchmarkRestHandler {
    @RequestMapping(value = "/getfile", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    BenchmarkRestHandler.RestHandlerResponse getFile();

    @Data
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class RestHandlerResponse {
        @JsonProperty("created")
        private long created;
        @JsonProperty("file")
        private byte[] file;
    }
}
