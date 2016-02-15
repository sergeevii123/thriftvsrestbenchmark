package benchmark;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by ilya on 16.02.16.
 */
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