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

import df.zhang.auth.constant.UserStateEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 自定义的用户信息载入类，当输入用户名在平台数据库中不存在时，允许类中抛出异常{@link UsernameNotFoundException}
 *
 * @author df.zhang Email: 84154025@qq.com
 * @date 2019-05-02
 * @since 1.0.0
 */
@Slf4j
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("SecurityContext {}", SecurityContextHolder.getContext().getAuthentication());
        log.info("用户名[{}]尝试登录。", username);
        if (!"admin".equals(username)) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(1L, "admin", "7445b0991419189b5c3848d2195f3cb9f99c3a25", UserStateEnum.ENABLED);
    }
}
