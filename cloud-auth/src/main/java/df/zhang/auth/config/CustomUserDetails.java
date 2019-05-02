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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;

/**
 * 自定义的{@link UserDetails}实现类，存放用户登录信息。
 * 用户校验不是在{@link UserDetailsService}中完成，而是在各种provider中。
 * 所以需要将用户名和密码存放进来，校验成功后会放入缓存（redis）。
 *
 * @author df.zhang Email: 84154025@qq.com
 * @date 2019-05-02
 * @since 1.0.0
 */
public class CustomUserDetails implements UserDetails {
    private long userId;
    private String username;
    private String password;
    private UserStateEnum state;

    public CustomUserDetails(long userId, String username, String password, UserStateEnum state) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.state = state;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public long getUserId() {
        return userId;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return state != UserStateEnum.EXPIRED;
    }

    @Override
    public boolean isAccountNonLocked() {
        return state != UserStateEnum.LOCKED;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return state != UserStateEnum.CREDENTIALS_EXPIRED;
    }

    @Override
    public boolean isEnabled() {
        return state == UserStateEnum.ENABLED;
    }
}
