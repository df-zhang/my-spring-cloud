package df.zhang.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthApplication implements CommandLineRunner {
    // Spring Boot 默认将日志框架logback作为SLF4J的实现
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

    /**
     * TODO
     * @param fdsfd
     * @return fdsff s
     * @author 84154025@qq.com
     */
    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("Spring Cloud Authorization Running...");
    }
}
