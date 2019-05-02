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
package df.zhang.auth.constant;

/**
 * 用户状态枚举类
 *
 * @author df.zhang Email: 84154025@qq.com
 * @date 2019-05-02
 * @since 1.0.0
 */
public enum UserStateEnum {

    /**
     * 用户正常
     */
    ENABLED(0),
    /**
     * 用户已禁用
     */
    DISABLED(1),
    /**
     * 用户被锁定
     */
    LOCKED(2),
    /**
     * 用户已过期
     */
    EXPIRED(3),
    /**
     * 项目授权已过期
     */
    CREDENTIALS_EXPIRED(4);

    private int state;

    UserStateEnum(int state) {
        this.state = state;
    }

    public int getState() {
        return this.state;
    }

    static UserStateEnum[] VALUES = UserStateEnum.values();

    public static UserStateEnum findByState(int state) {
        for (UserStateEnum userStateEnum : VALUES) {
            if (userStateEnum.state == state) {
                return userStateEnum;
            }
        }
        return ENABLED;
    }
}
