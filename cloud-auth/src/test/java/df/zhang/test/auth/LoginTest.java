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
package df.zhang.test.auth;

import df.zhang.api.UserApi;
import df.zhang.api.dto.input.UserInputDTO;
import df.zhang.api.dto.output.UserOutputDTO;
import df.zhang.base.pojo.ApiResult;
import df.zhang.test.BaseTest;
import org.junit.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.annotation.Resource;
import java.util.List;

/**
 * 登录测试类
 *
 * @author df.zhang Email: 84154025@qq.com
 * @date 2019-05-04
 * @since 1.0.0
 */
public class LoginTest extends BaseTest {
    protected MockHttpSession session;
    @Resource
    private UserApi userApi;

    @Test
    public void test() throws Exception {
        // 登录
        MvcResult result = mockMvc.perform(SecurityMockMvcRequestBuilders.formLogin().user("admin").password("admin"))
                .andReturn();
//        System.out.println(result.getResponse().getContentAsString());
//        session = (MockHttpSession) result.getRequest().getSession();
//        // 匿名访问
//        result = mockMvc.perform(MockMvcRequestBuilders.get("/anonymous")).andReturn();
//        System.out.println(result.getResponse().getContentAsString());
//        // 登录后请求仅匿名可访问的资源
//        result = mockMvc.perform(MockMvcRequestBuilders.get("/anonymous").session(session)).andReturn();
//        System.out.println(result.getResponse().getContentAsString());
//        // 未登录请求需登录后才能访问的资源
//        result = mockMvc.perform(MockMvcRequestBuilders.get("/authenticated")).andReturn();
//        System.out.println(result.getResponse().getContentAsString());
//        // 登录后请求登录后可访问的资源
//        result = mockMvc.perform(MockMvcRequestBuilders.get("/authenticated").session(session)).andReturn();
//        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    public void testApi() {
//        ApiResult<List<UserOutputDTO>> apiResult = userApi.users(new UserInputDTO());
//        apiResult.ifSuccess(list -> {
//            System.out.println(list.size());
//        });
        userApi.getByUsername("user").ifSuccess(System.out::println);
    }
}
