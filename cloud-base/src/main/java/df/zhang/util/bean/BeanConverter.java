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
package df.zhang.util.bean;

import df.zhang.util.ReflectUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.core.Converter;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author df.zhang Email: 84154025@qq.com
 * {@link Converter}实现类，用于自定义属性转换器。
 * 当该属性转换器被应用之后，cglib自带的属性转换器会失效，务必完善该转换器方法。
 * @author df.zhang Email: 84154025@qq.com
 * @date 2019-05-02
 * @since 1.0.0
 */
@Slf4j
public class BeanConverter implements Converter {
    private static final Set<Class<?>> PRIMITIVES = new HashSet<>();

    private Class<?> sourceBeanClass;
    private Class<?> targetBeanClass;

    public BeanConverter(Class<?> sourceBeanClass, Class<?> targetBeanClass) {
        this.sourceBeanClass = sourceBeanClass;
        this.targetBeanClass = targetBeanClass;
    }

    @Override
    public Object convert(Object value, Class target, Object targetSetMethodName) {

        if (value == null) {
            // 如果目标属性类型是基本数据类型，直接返回null，会自动设置为默认值。
            return null;
        }
        Class<?> source = value.getClass();
        if (log.isDebugEnabled()) {
            log.debug("{} copy to {}", source.getName(), target.getName());
        }
        if (source.isArray()) {
            // 如果目标对象是数组。 将数组复制为数组
            if (target.isArray()) {
                // 根据数组的协变性，任何数组都可以转换为Object[]
                return BeanUtils.copyArray((Object[]) value, target.getComponentType());
            }
            if (Collection.class.isAssignableFrom(target)) {
                Collection collection = (Collection) ReflectUtils.newInstance(target);
                Class<?> genericType = getGenericType((String) targetSetMethodName, targetBeanClass);
                BeanUtils.copyArrayToCollection((Object[]) value, collection, genericType);
                return collection;
            }
        }

        if (Collection.class.isAssignableFrom(source)) {
            // 如果源对象是集合
            Collection<?> sourceValues = (Collection) value;
            // 如果目标对象是数组。 将集合复制为数组
            if (target.isArray()) {
                // 取得目标数组对象类型（绝对会有的，不然数组类型是?[]）
                return BeanUtils.copyCollectionToArray(sourceValues, target.getComponentType());
            }
            if (target != Object.class && !Collection.class.isAssignableFrom(target)) {
                // 如果目标对象类型不是Object，也不是Collection集合的子类。直接返回空，无法处理。
                return null;
            }
            if (target == Object.class) {
                // 目标对象是Object时，已源对象为标准创建集合。上文已有判断源对象是集合
                return BeanUtils.copyCollection(sourceValues);
            }
            Class<?> genericType = getGenericType((String) targetSetMethodName, targetBeanClass);
            if (genericType == null) {
                // 没有泛型时使用源对象的class进行转换。不在循环中判断，提升一点微乎其微的性能。
                return BeanUtils.copyCollection(sourceValues);
            } else {
                // 有泛型时转换为泛型对应对象
                return BeanUtils.copyCollection(sourceValues, genericType);
            }
        }

        if (Map.class.isAssignableFrom(source)) {
            if (Collection.class.isAssignableFrom(target)) {

                Collection collection = ((Map) value).values();
                Class<?> genericType = getGenericType((String) targetSetMethodName, targetBeanClass);
                if (genericType == null) {
                    // 没有泛型时使用源对象的class进行转换。不在循环中判断，提升一点微乎其微的性能。
                    return BeanUtils.copyCollection(collection);
                } else {
                    // 有泛型时转换为泛型对应对象
                    return BeanUtils.copyCollection(collection, genericType);
                }
            }

            if (Map.class.isAssignableFrom(target)) {
                // TODO 复制Map
                return value;
            }
        }
        return BeanUtils.copyObject(value, target);
    }

    static boolean isPrimitive(Class<?> clazz) {
        return clazz.isPrimitive() || PRIMITIVES.contains(clazz);
    }

    static Class<?> getGenericType(String setMethodName, Class<?> targetClass) {
        Class<?> genericType = null;
        // 目标对象是集合时，上文已将非Object和非集合的处理返回null
        // 取得字段名称，copy属性要求get/set方法都有。
        String fieldName = setMethodName.toString().replaceFirst("set", "");
        fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
        // 取得字段，从targetClass中获取。可以考虑缓存该Converter类，提升一点性能
        Field field = ReflectUtils.getField(targetClass, fieldName);
        if (field != null) {
            // 如果有泛型就取得泛型类型，泛型类型只能通过类成员属性获取，非类成员属性（临时变量和方法参数）无法获取到泛型。
            Type type = field.getGenericType();
            if (type instanceof ParameterizedType) {
                String genericTypeClassName = ((ParameterizedType) type).getActualTypeArguments()[0].getTypeName();
                try {
                    genericType = Thread.currentThread().getContextClassLoader().loadClass(genericTypeClassName);
                } catch (ClassNotFoundException ignored) {
                }
            }
        }
        return genericType;
    }

    static {
        BeanConverter.PRIMITIVES.add(String.class);
        BeanConverter.PRIMITIVES.add(Byte.class);
        BeanConverter.PRIMITIVES.add(Short.class);
        BeanConverter.PRIMITIVES.add(Integer.class);
        BeanConverter.PRIMITIVES.add(Float.class);
        BeanConverter.PRIMITIVES.add(Double.class);
        BeanConverter.PRIMITIVES.add(Long.class);
        BeanConverter.PRIMITIVES.add(Boolean.class);
        BeanConverter.PRIMITIVES.add(Character.class);
        BeanConverter.PRIMITIVES.add(CharSequence.class);
        BeanConverter.PRIMITIVES.add(java.util.Date.class);
        BeanConverter.PRIMITIVES.add(java.sql.Date.class);
        BeanConverter.PRIMITIVES.add(LocalDate.class);
        BeanConverter.PRIMITIVES.add(LocalTime.class);
        BeanConverter.PRIMITIVES.add(LocalDateTime.class);
        BeanConverter.PRIMITIVES.add(Instant.class);
        BeanConverter.PRIMITIVES.add(OffsetTime.class);
        BeanConverter.PRIMITIVES.add(OffsetDateTime.class);
        BeanConverter.PRIMITIVES.add(Map.class);
    }
}
