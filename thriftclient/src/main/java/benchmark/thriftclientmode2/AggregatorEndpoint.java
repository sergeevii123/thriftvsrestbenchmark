package benchmark.thriftclientmode2;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ilya on 18.02.16.
 */
@Profile("mode2")
@RestController
public class AggregatorEndpoint {

    public String name;

    private static int counter;

    @RequestMapping(value = "/start", method = RequestMethod.GET)
    public void start() {
        //each new start creates new statistic
        name = "get-file-" + counter++;
    }

}
