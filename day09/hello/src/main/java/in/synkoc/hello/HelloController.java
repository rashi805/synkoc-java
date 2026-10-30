package in.synkoc.hello;

import java.util.Map;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello from SYNKOC!";
    }

    @GetMapping("/api/hello")
    public Map<String, String> apiHello() {
        return Map.of("message", "Hello from SYNKOC!");
    }

    @GetMapping("/api/time")
    public TimeResponse apiTime() {

        ZoneId zone = ZoneId.of("Asia/Kolkata");
        ZonedDateTime now = ZonedDateTime.now(zone);

        String date = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String time = now.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        return new TimeResponse(date, time, zone.getId());
    }
}

record TimeResponse(String date, String time, String zone) {}