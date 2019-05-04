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
package df.zhang.base.exception;

/**
 * TODO
 *
 * @author df.zhang Email: 84154025@qq.com
 * @date 2019-05-04
 * @since 1.0.0
 */
public enum BasicErrorCode implements IErrorCode {
    /**
     * 成功，code=10000，msg=success，category=null，无大类目
     */
    SUCCESS("10000", "success", null),
    USER("11000", "user", null),
    USER_AUTH("11100", "user.login", USER),
    USER_NOT_LOGIN("11101", "not_login", USER_AUTH),
    USER_UNAUTHORIZED("11102", "unauthorized", USER_AUTH),
    USERNAME_NOTFOUND("11104", "username.not_found", USER_AUTH),
    ;

    private String code;
    private String msg;
    private IErrorCode parent;

    BasicErrorCode(String code, String msg, IErrorCode parent) {
        this.code = code;
        this.msg = msg;
        this.parent = parent;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public IErrorCode getParent() {
        return parent;
    }
}
