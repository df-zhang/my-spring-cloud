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

import df.zhang.util.bean.BeanUtils;

import java.io.Serializable;

/**
 * POJO顶层接口，提供序列化接口的继承，以及默认方法
 *
 * @author df.zhang Email: 84154025@qq.com
 * @date 2019-04-22
 * @since 1.0.0
 */
public interface IModel extends Serializable {

    /**
     * 为所有IModel的实例提供对象属性copy方法
     *
     * @param rClass 目标结果类型
     * @return R 目标结果实例对象
     * @date 2019-04-22 23:40:47
     * @author df.zhang
     * @since 1.0.0
     */
    default <R> R cast(Class<R> rClass) {
        return BeanUtils.copy(this, rClass);
    }
}
