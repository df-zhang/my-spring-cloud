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
package df.zhang.test.json;

import com.fasterxml.jackson.core.type.TypeReference;
import df.zhang.util.JsonUtils;
import df.zhang.util.date.DatePattern;

import java.util.Date;

/**
 * TODO
 *
 * @author df.zhang Email: 84154025@qq.com
 * @version 1.0.0
 * @date 2019-04-21
 */
public class JsonTest {
    public static void main(String[] args) {
        System.out.println(DatePattern.DATETIME_YYYY_MM_DD_HH_MM_SS.toString());
        System.out.println(JsonUtils.serialize(new Date()));
        JsonUtils.deserialize("2019-04-21 23:34:51", Date.class).ifPresent(System.out::println);
        JsonUtils.deserialize("{}", new TypeReference<Object>() {
        }).ifPresent(System.out::println);
    }
}
