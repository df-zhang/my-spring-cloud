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
package df.zhang.test.util;

import df.zhang.auth.config.CustomPasswordEncoder;

import java.util.UUID;
import java.util.stream.IntStream;

/**
 * TODO
 *
 * @author df.zhang Email: 84154025@qq.com
 * @date 2019-05-02
 * @since 1.0.0
 */
public class PasswordEncodeTest {
    public static void main(String[] args) {
        CustomPasswordEncoder encoder = new CustomPasswordEncoder();
        System.out.println(encoder.encode("admin"));

    }
}
