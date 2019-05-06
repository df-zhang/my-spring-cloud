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
package df.zhang.api.user;

import com.google.common.collect.Lists;
import df.zhang.api.dto.input.UserInputDTO;
import df.zhang.base.pojo.ApiResult;
import df.zhang.api.UserApi;
import df.zhang.api.dto.output.UserOutputDTO;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * TODO
 *
 * @author df.zhang Email: 84154025@qq.com
 * @date 2019-05-06
 * @since 1.0.0
 */
@RestController
public class UserApiImpl implements UserApi {
    @Override
    public ApiResult<UserOutputDTO> getByUsername(String username) {
        UserOutputDTO userOutputDTO = new UserOutputDTO();
        userOutputDTO.setUsername("admin");
        userOutputDTO.setPassword("7445b0991419189b5c3848d2195f3cb9f99c3a25");
        return ApiResult.<UserOutputDTO>success().res(userOutputDTO);
    }

    @Override
    public ApiResult<List<UserOutputDTO>> users(UserInputDTO inputDTO) {
        UserOutputDTO userOutputDTO = new UserOutputDTO();
        userOutputDTO.setUsername("users");
        userOutputDTO.setPassword("users");
        return ApiResult.<List<UserOutputDTO>>success().res(Lists.newArrayList(userOutputDTO));
    }
}
