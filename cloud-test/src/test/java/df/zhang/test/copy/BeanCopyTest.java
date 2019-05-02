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
package df.zhang.test.copy;

import df.zhang.util.bean.BeanUtils;
import df.zhang.util.ReflectUtils;
import net.sf.cglib.beans.BeanGenerator;

import java.util.*;

/**
 * TODO
 *
 * @author df.zhang Email: 84154025@qq.com
 * @version 1.0.0
 * @date 2019-04-23
 */
public class BeanCopyTest {
    private static byte c;

    public static void main(String[] args) throws InterruptedException {
//        net.sf.cglib.empty.Object$$BeanCopierByCGLIB$$df6dbed5@1e81f4dc
//        net.sf.cglib.empty.Object$$BeanCopierByCGLIB$$df6dbed5@4d591d15
//        net.sf.cglib.empty.Object$$BeanCopierByCGLIB$$df6dbed5@65ae6ba4
//        LinkedList linkedList =   ReflectUtils.newInstance(LinkedList.class);
//        System.out.println(linkedList.getClass());
//
//        Field field = ReflectUtils.getField(Bean2.class, "bean1Map");
//
//        if (field != null) {
//            Type type = field.getGenericType();
//            System.out.println(field.getGenericType().getClass());
//            if (type instanceof ParameterizedType) {
//                System.out.println(Arrays.toString(((ParameterizedType) type).getActualTypeArguments()));
//                String genericTypeClassName = ((ParameterizedType) type).getActualTypeArguments()[0].getTypeName();
//                try {
//                    System.out.println(Thread.currentThread().getContextClassLoader().loadClass(genericTypeClassName));;
//                } catch (ClassNotFoundException ignored) {
//                }
//            }
//        }
        String[] strs = {"1", "2", "3", "4", "5"};
        System.out.println(Arrays.toString(BeanUtils.copyArray(strs, String.class)));

    }
}
