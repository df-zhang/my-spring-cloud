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
package df.zhang.auth;

import df.zhang.BasePackage;
import df.zhang.api.ApiBasePackage;
import df.zhang.base.pojo.ApiResult;
import df.zhang.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 鉴权模块启动类。{@link BasePackage}为root包下的类文件，为各模块的Application指引包名路径。
 *
 * @author df.zhang Email: 84154025@qq.com
 * @date 2019-04-21
 * @since 1.0.0
 */
@EnableFeignClients(basePackageClasses = ApiBasePackage.class)
@ComponentScan(basePackageClasses = ApiBasePackage.class)
@SpringBootApplication(scanBasePackageClasses = BasePackage.class)
@Slf4j
@RestController
public class AuthApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

    /**
     * 使用自定义的ObjectMapper将对象序列化为JSON字符串，参考{@link JsonUtils}
     *
     * @return org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
     * @date 2019-05-04 03:21
     * @author df.zhang
     * @since 1.0.0
     */
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        return new MappingJackson2HttpMessageConverter(JsonUtils.getObjectMapper());
    }

    @Override
    public void run(String... args) {
        log.info("My Cloud Authorization Running...");
    }

    /**
     * 配置需要登录认证后访问的controller
     *
     * @return df.zhang.base.pojo.ApiResult&lt;java.lang.String&gt;
     * @date 2019-05-04 03:22
     * @author df.zhang
     * @since 1.0.0
     */
    @GetMapping("authenticated")
    public ApiResult<String> testAuthenticated() {
        return ApiResult.<String>success().res("authenticated");
    }

    /**
     * 配置可以匿名访问的controller
     *
     * @return df.zhang.base.pojo.ApiResult&lt;java.lang.String&gt;
     * @date 2019-05-04 03:22
     * @author df.zhang
     * @since 1.0.0
     */
    @GetMapping("anonymous")
    public ApiResult<String> testAnonymous() {
        log.info("{}", SecurityContextHolder.getContext().getAuthentication());
        return ApiResult.<String>success().res("anonymous");
    }
}
