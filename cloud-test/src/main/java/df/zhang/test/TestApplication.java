/*
 * Copyright [2019] [df.zhang]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package df.zhang.test;

import df.zhang.BasePackage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * TODO
 *
 * @author df.zhang Email: 84154025@qq.com
 * @date 2019-05-06
 * @since 1.0.0
 */
@SpringBootApplication(scanBasePackageClasses = BasePackage.class)
@Slf4j
public class TestApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
    @Override
    public void run(String... args) {
        log.info("My Cloud Test Running...");
    }
}
