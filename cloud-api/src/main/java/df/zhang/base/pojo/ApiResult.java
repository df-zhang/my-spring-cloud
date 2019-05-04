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
package df.zhang.base.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import df.zhang.base.exception.BasicErrorCode;
import df.zhang.base.exception.IErrorCode;
import lombok.*;

import java.io.Serializable;

/**
 * API结果包装类，预计采用protocol buffer来输出API调用结果。
 *
 * @author df.zhang Email: 84154025@qq.com
 * @date 2019-04-22
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ApiResult<T> implements Serializable {
    private String code;
    private String msg;
    private String errCode;
    private String errMsg;
    private T res;

    public static <T> ApiResult<T> success() {
        return new ApiResult<T>().errorCode(BasicErrorCode.SUCCESS);
    }

    public ApiResult<T> errorCode(IErrorCode errorCode) {
        if (errorCode == null) {
            return this;
        }

        IErrorCode parent = errorCode.getParent();
        if (parent == null) {
            this.code = errorCode.getCode();
            this.msg = errorCode.getMsg();
        } else {
            this.code = parent.getCode();
            this.msg = parent.getMsg();
            this.errCode = errorCode.getCode();
            this.errMsg = errorCode.getMsg();
        }
        return this;
    }

    public ApiResult<T> res(T res) {
        this.res = res;
        return this;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return BasicErrorCode.SUCCESS.getCode().equals(this.code);
    }
}
