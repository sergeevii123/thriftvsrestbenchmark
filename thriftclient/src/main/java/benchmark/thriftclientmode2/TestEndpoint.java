package benchmark.thriftclientmode2;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ilya on 18.02.16.
 */
@Profile("mode2")
@RestController
public class TestEndpoint {

    public String name;
    public int fileLength;

    private static int counter;

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public void start(@RequestParam("threadcount") int threadCount,
                      @RequestParam("duration") int duration,
                      @RequestParam("filelength") int fileLength) {
        //each new start creates new statistic
        name = String.format("test-%d-tc-%d-d-%d-fl-%d", counter++, threadCount, duration, fileLength);
        this.fileLength = fileLength;
    }

}
