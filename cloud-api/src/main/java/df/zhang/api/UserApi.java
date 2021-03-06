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
package df.zhang.api;

import df.zhang.api.dto.input.UserInputDTO;
import df.zhang.api.dto.output.UserOutputDTO;
import df.zhang.base.BaseApi;
import df.zhang.base.pojo.ApiResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 管理后台的用户模块远程调用接口。该接口由管理后台应用实现。
 *
 * @author df.zhang Email: 84154025@qq.com
 * @date 2019-04-22
 * @since 1.0.0
 */
@FeignClient(value = "user-api", contextId = "cloud-test", url = "http://localhost", fallbackFactory = UserApiFallbackFactory.class)
public interface UserApi extends BaseApi {

    /**
     * 根据用户名获取用户信息，该用户信息包含密码。用于鉴权模块的用户登录。
     *
     * @param username 用户名
     * @return df.zhang.base.pojo.ApiResult&lt;df.zhang.api.dto.output.UserOutputDTO&gt;
     * @date 2019-04-22 00:30
     * @author df.zhang
     * @since 1.0.0
     */
    @GetMapping("user")
    ApiResult<UserOutputDTO> getByUsername(@RequestParam("username") String username);

    @PostMapping("users")
    ApiResult<List<UserOutputDTO>> users(UserInputDTO inputDTO);

    @GetMapping("group")
    default ApiResult<String> getGroup() {
        return ApiResult.<String>success().res("fdsfds");
    }
}
