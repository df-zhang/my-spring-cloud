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
import df.zhang.api.ApiBasePackage;
import df.zhang.api.config.FeignConfigurer;
import df.zhang.api.dto.output.UserOutputDTO;
import df.zhang.base.pojo.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author df.zhang Email: 84154025@qq.com
 * @date 2019-05-06
 * @since 1.0.0
 */
@SpringBootApplication(scanBasePackageClasses = BasePackage.class)
@ComponentScan(basePackageClasses = ApiBasePackage.class)
@EnableFeignClients(basePackageClasses = ApiBasePackage.class)
@Import(FeignConfigurer.class)
@Slf4j
@RestController
public class TestApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

    @Override
    public void run(String... args) {
        log.info("My Cloud Test Running...");
    }

//    @GetMapping("user")
//    public ApiResult<UserOutputDTO> getByUsername(@RequestParam("username") String username) {
//        UserOutputDTO userOutputDTO = new UserOutputDTO();
//        userOutputDTO.setUsername("admin");
//        userOutputDTO.setPassword("7445b0991419189b5c3848d2195f3cb9f99c3a25");
//        userOutputDTO.setNickname("WebMVC");
//        return ApiResult.<UserOutputDTO>success().res(userOutputDTO);
//    }
}
