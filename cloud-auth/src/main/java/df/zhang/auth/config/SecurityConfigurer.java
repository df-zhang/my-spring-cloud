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
package df.zhang.auth.config;

import df.zhang.base.pojo.ApiResult;
import df.zhang.util.JsonUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;


/**
 * Spring Security配置类
 *
 * @author df.zhang Email: 84154025@qq.com
 * @version 1.0.0
 * @date 2019-04-21
 */
@EnableWebSecurity
@Configuration
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    /**
     * 使用自定义的密码加密工具，其实现类为{@link CustomPasswordEncoder}
     *
     * @return org.springframework.security.crypto.password.PasswordEncoder
     * @date 2019-05-02 17:23:25
     * @author df.zhang
     * @since 1.0.0
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new CustomPasswordEncoder();
    }

    @Resource
    private PasswordEncoder passwordEncoder;

    /**
     * 这段代码描述为Spring-Security启动项目创建一个admin账户，角色为ADMIN。
     * 该用户信息保存在内存中，项目停止时会被清除。
     * 使用密码加密工具后，内存账户的密码不能在直接配置为明文，需要进行加密。
     *
     * @return org.springframework.security.core.userdetails.UserDetailsService
     * @date 2019-05-02 16:12:05
     * @author df.zhang
     * @since 1.0.0
     */
    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsServiceImpl();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 禁用匿名
                .anonymous().disable()
                // 配置所有请求都至少是登录用户才能访问
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                // 配置表单登录，此处可以改登录路径
                .formLogin()
                // 配置成功帮助类，输出2000
                .successHandler((request, response, authentication) -> {
                    ApiResult<CustomUserDetails> apiResult = new ApiResult<>();
                    apiResult.setCode(2000);
                    apiResult.setMsg("Success");
                    apiResult.setRes((CustomUserDetails) authentication.getPrincipal());
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                    response.getWriter().print(JsonUtils.serialize(apiResult));
                    response.getWriter().flush();
                })
                // 配置失败帮助类，输出2001
                .failureHandler(((request, response, exception) -> {
                    ApiResult<String> apiResult = new ApiResult<>();
                    apiResult.setCode(2001);
                    apiResult.setMsg("Failed");
                    apiResult.setErrCode("Username.NotFound");
                    apiResult.setErrMsg(exception.getMessage());
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                    response.getWriter().print(JsonUtils.serialize(apiResult));
                    response.getWriter().flush();
                }));
    }

    //    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//
//                .formLogin()
//                .successHandler((request, response, authentication) -> {
//                    System.out.println("Success");
//                    response.setStatus(200);
//                    response.getWriter().println("Success");
//                })
//                .and()
//                .csrf().disable();
//    }

}
