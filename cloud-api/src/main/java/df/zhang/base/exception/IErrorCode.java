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
public interface IErrorCode {

    /**
     * 返回码或明细返回码
     *
     * @return java.lang.String
     * @date 2019-05-04 02:51
     * @author df.zhang
     * @since 1.0.0
     */
    String getCode();

    /**
     * 返回码或明细返回码描述
     *
     * @return java.lang.String
     * @date 2019-05-04 02:51
     * @author df.zhang
     * @since 1.0.0
     */
    String getMsg();

    /**
     * 父级（大类）返回码枚举，当父级（大类）返回码不为空时，对应的返回码和返回码描述为父级（大类）返回码，
     *
     * @return df.zhang.base.exception.IErrorCode
     * @date 2019-05-04 02:52
     * @author df.zhang
     * @since 1.0.0
     */
    IErrorCode getParent();
}
