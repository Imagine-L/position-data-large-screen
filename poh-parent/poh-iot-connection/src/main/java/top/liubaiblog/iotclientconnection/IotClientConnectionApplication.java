package top.liubaiblog.iotclientconnection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync(proxyTargetClass = true)
public class IotClientConnectionApplication {

    public static void main(String[] args) {
        SpringApplication.run(IotClientConnectionApplication.class, args);
    }

}
