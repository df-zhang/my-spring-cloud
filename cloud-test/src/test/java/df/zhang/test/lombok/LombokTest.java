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
package df.zhang.test.lombok;

import df.zhang.base.pojo.ApiResult;
import df.zhang.manage.dto.output.UserOutputDTO;

/**
 * TODO
 *
 * @author df.zhang Email: 84154025@qq.com
 * @version 1.0.0
 * @date 2019-04-22
 */
public class LombokTest {
    public static void main(String[] args) {
        ApiResult<UserOutputDTO> apiResult = new ApiResult<>();
        apiResult.setRes(new UserOutputDTO("f", "f", "F","f"));
    }
}
