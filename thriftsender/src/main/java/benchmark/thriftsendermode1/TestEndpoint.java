package benchmark.thriftsendermode1;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * Created by ilya on 18.02.16.
 */
@Profile("mode1")
@RestController
public class TestEndpoint {

    public byte[] array;

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public void start(@RequestParam("filelength") int fileLength){
        Random random = new Random();
        array = new byte[fileLength];
        random.nextBytes(array);
    }

}
