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

import df.zhang.base.exception.BasicErrorCode;
import df.zhang.base.pojo.ApiResult;
import df.zhang.util.JsonUtils;
import df.zhang.util.PasswordUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletResponse;


/**
 * Spring Security配置类
 *
 * @author df.zhang Email: 84154025@qq.com
 * @date 2019-04-21
 * @since 1.0.0
 */
@EnableWebSecurity
@Configuration
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    /**
     * 使用自定义的密码加密工具
     *
     * @return org.springframework.security.crypto.password.PasswordEncoder
     * @date 2019-05-02 17:23
     * @author df.zhang
     * @since 1.0.0
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return PasswordUtils.encode(rawPassword);
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encode(rawPassword).equals(encodedPassword);
            }
        };
    }

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
                .csrf().disable()
                .authorizeRequests()
                // 配置/anonymous开头的请求仅可匿名访问
                .antMatchers("/anonymous/**")
                .anonymous()
                // 配置所有请求需登录访问
                .anyRequest().authenticated()
                // 配置匿名登录
                .and().anonymous()
                // 配置表单登录，此处可以改登录路径
                .and().formLogin()
                // 配置成功处理类
                .successHandler((request, response, authentication) -> {
                    ApiResult<CustomUserDetails> apiResult = ApiResult.<CustomUserDetails>success()
                            .res((CustomUserDetails) authentication.getPrincipal());
                    apiResult.setMsg("登录成功");
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                    response.getWriter().print(JsonUtils.serialize(apiResult));
                    response.getWriter().flush();
                })
                // 配置失败处理类
                .failureHandler(((request, response, exception) -> {
                    ApiResult<String> apiResult = new ApiResult<String>()
                            .errorCode(BasicErrorCode.USERNAME_NOTFOUND);
                    apiResult.setMsg("用户名或密码错误");
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                    response.getWriter().print(JsonUtils.serialize(apiResult));
                    response.getWriter().flush();
                }))
                // 配置未授权异常处理器
                .and().exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> {
                    ApiResult<String> apiResult = new ApiResult<String>()
                            .errorCode(BasicErrorCode.USER_NOT_LOGIN);
                    apiResult.setMsg("用户未登录");
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                    response.getWriter().print(JsonUtils.serialize(apiResult));
                    response.getWriter().flush();
                })
                // 配置拒绝访问异常处理器
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    ApiResult<String> apiResult = new ApiResult<String>()
                            .errorCode(BasicErrorCode.USER_UNAUTHORIZED);
                    apiResult.setMsg("无权限访问");
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                    response.getWriter().print(JsonUtils.serialize(apiResult));
                    response.getWriter().flush();
                });
    }

    /**
     * 配置Security需要忽略的访问路径，所有忽略的访问路径都不会再经过Security的任何Filter
     *
     * @param web {@link WebSecurity}
     * @date 2019-05-04 16:14
     * @author df.zhang
     * @since 1.0.0
     */
    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                // 使用浏览器访问任意路径，都会向服务器拿取页面图标信息。此处将其忽略
                .antMatchers("/favicon.ico");
    }
}
