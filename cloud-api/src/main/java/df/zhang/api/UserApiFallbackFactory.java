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
import df.zhang.base.pojo.ApiResult;
import df.zhang.api.dto.output.UserOutputDTO;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * TODO
 *
 * @author df.zhang Email: 84154025@qq.com
 * @date 2019-05-06
 * @since 1.0.0
 */
@Component
@Slf4j
public class UserApiFallbackFactory implements FallbackFactory<UserApi> {

    @Override
    public UserApi create(Throwable throwable) {
        log.error(throwable.getMessage(), throwable);
        return new UserApi() {
            @Override
            public ApiResult<UserOutputDTO> getByUsername(String username) {
                return ApiResult.success();
            }

            @Override
            public ApiResult<List<UserOutputDTO>> users(UserInputDTO inputDTO) {
                return ApiResult.success();
            }
        };
    }
}
