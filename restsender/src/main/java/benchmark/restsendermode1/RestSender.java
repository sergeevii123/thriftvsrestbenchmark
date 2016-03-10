package benchmark.restsendermode1;

import benchmark.FileAndStart;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * Created by ilya on 16.02.16.
 */
@Profile("mode1")
@RestController
public class RestSender {

    private byte[] array;

    private ObjectMapper mapper = new ObjectMapper();

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public void start(@RequestParam("filelength") int fileLength) {
        Random random = new Random();
        array = new byte[fileLength];
        random.nextBytes(array);
    }

    @RequestMapping(value = "/getfile", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] getFile() throws JsonProcessingException {
        return mapper.writeValueAsBytes(new FileAndStart(System.currentTimeMillis(), array));
    }

}
