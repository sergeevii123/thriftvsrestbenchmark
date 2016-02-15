package benchmark;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * Created by ilya on 16.02.16.
 */
@RestController
public class RestHandler {

    private byte[] array;

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public void start(@RequestParam("filelength") int fileLength){
        Random random = new Random();
        array = new byte[fileLength];
        random.nextBytes(array);
    }

    @RequestMapping(value = "/getfile", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public RestHandlerResponse getFile(){
        return RestHandlerResponse.builder().created(System.currentTimeMillis()).file(array).build();
    }

}
