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
package df.zhang.util;

import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.core.Converter;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * <p>对象属性复制转换工具类。
 * <p>使用须知：本工具类使用cglib包中的{@link BeanCopier}实现，性能优于apache与spring提供的基于反射机制实现的BeanUtils。<br>
 * 应根据实际场景调整{@link BeanConverter#convert(Object, Class, Object)}方法。
 *
 * <p>{@link BeanCopier}复制属性时需要有对应的get和set方法，且set方法的返回类型必须是void。
 * 该硬性条件参考{@link net.sf.cglib.core.ReflectUtils#getBeanSetters(Class)}方法的具体实现{@link PropertyDescriptor#getWriteMethod()}：
 *
 * <blockquote><pre>
 * writeMethod = Introspector.findMethod(cls, writeMethodName, 1, args);
 * if (writeMethod != null) {
 *      if (!writeMethod.getReturnType().equals(void.class)) {
 *          writeMethod = null;
 *      }
 * }
 * </blockquote></pre>
 *
 * <p>结合Lombok使用时须关闭其访问器链配置：lombok.accessors.chain=false，请勿编写有返回类型的set方法。
 *
 * @author df.zhang Email: 84154025@qq.com
 * @date 2019-04-22
 * @since 1.0.0
 */
@SuppressWarnings("unchecked")
public final class BeanUtils {

    public static <R> R copy(Object source, Class<R> rClass) {
        assert !Objects.isNull(source);
        assert !Objects.isNull(rClass);
        R r = ReflectUtils.newInstance(rClass);
        copy(source, r);
        return r;
    }

    public static <R> List<R> copyList(Collection source, Class<R> rClass) {
        assert !Objects.isNull(source);
        assert !Objects.isNull(rClass);
        List r = ReflectUtils.newInstance(ArrayList.class);
        if (r == null) {
            return new ArrayList<>(0);
        }
        for (Object sourceValue : source) {
            r.add(copy(sourceValue, rClass));
        }
        return r;
    }

    public static <R> Collection<R> copyCollection(Collection source, Class<R> rClass) {
        return copyList(source, rClass);
    }

    public static void copy(Object source, Object target) {
        assert !Objects.isNull(source);
        assert !Objects.isNull(target);
        getCopier(source.getClass(), target.getClass())
                .copy(source, target, new BeanConverter(source.getClass(), target.getClass()));
    }

    private static BeanCopier getCopier(Class<?> sourceClass, Class<?> targetClass) {
        String key = sourceClass.getName() + "@~df.zhang~@" + targetClass.getName();
        if (BEAN_COPIER_MAP.containsKey(key)) {
            return BEAN_COPIER_MAP.get(key);
        } else {
            BeanCopier copier = BeanCopier.create(sourceClass, targetClass, true);
            BeanCopier old = BEAN_COPIER_MAP.putIfAbsent(key, copier);
            if (old != null) {
                return old;
            }
            return copier;
        }
    }

    private static final ConcurrentMap<String, BeanCopier> BEAN_COPIER_MAP = new ConcurrentHashMap<>(0);

    /**
     * @author df.zhang Email: 84154025@qq.com
     * @link Converter}实现类，用于自定义属性转换器。
     * 当该属性转换器被应用之后，cglib自带的属性转换器会失效，务必完善该转换器方法。
     * @date 2019-04-23
     * @since 1.0.0
     */
    public static class BeanConverter implements Converter {
        private static final Set<Class<?>> PRIMITIVES = new HashSet<>();

        private Class<?> sourceClass;
        private Class<?> targetClass;

        public BeanConverter(Class<?> sourceClass, Class<?> targetClass) {
            this.sourceClass = sourceClass;
            this.targetClass = targetClass;
        }

        @Override
        public Object convert(Object value, Class target, Object targetSetMethodName) {
            // TODO 方法待优化，待完善。
            System.out.println(value + "target=" + target.getName() + ", targetSetMethodName=" + targetSetMethodName);
            if (value == null) {
                // 如果目标属性类型是基本数据类型，直接返回null，会自动设置为默认值。
                return null;
            }
            Class<?> source = value.getClass();

            if (Collection.class.isAssignableFrom(source)) {
                // 如果源对象是集合
                Collection<?> sourceValues = (Collection) value;
                int size = sourceValues.size();
                // 如果目标对象是数组
                if (target.isArray()) {
                    // 取得目标数组对象类型（绝对会有的，不然数组类型是?[]）
                    Class<?> componentType = target.getComponentType();
                    // 创建数组
                    Object result = Array.newInstance(componentType, size);
                    int i = 0;
                    if (componentType == Object.class) {
                        // 如果目标数组对象类型是Object。 不在循环中判断，提升一点微乎其微的性能。
                        for (Object sourceValue : sourceValues) {
                            Array.set(result, i++, BeanUtils.copy(sourceValue, sourceValue.getClass()));
                        }
                    } else {
                        // 复制成目标数组对象类型。
                        for (Object sourceValue : sourceValues) {
                            Array.set(result, i++, BeanUtils.copy(sourceValue, componentType));
                        }
                    }
                    return result;
                }
                if (target != Object.class && !Collection.class.isAssignableFrom(target)) {
                    // 如果目标对象类型不是Object，也不是Collection集合的子类。直接返回空，无法处理。
                    return null;
                }

                Collection result = null;
                Class<?> genericType = null;
                if (target == Object.class) {
                    // 目标对象是Object时，已源对象为标准创建集合。上文已有判断源对象是集合
                    result = (Collection) ReflectUtils.newInstance(source);
                } else {
                    // 目标对象是集合时，上文已将非Object和非集合的处理返回null
                    // 取得字段名称，copy属性要求get/set方法都有。
                    String fieldName = targetSetMethodName.toString().replaceFirst("set", "");
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
                }
                if (target.isInterface()) {
                    // 如果目标对象是接口，一般写法为List<T> list。创建默认子类集合
                    if (List.class.isAssignableFrom(target)) {
                        result = new ArrayList(size);
                    } else if (Set.class.isAssignableFrom(target)) {
                        result = new LinkedHashSet(size);
                    }
                } else {
                    // 直接创建对应集合。
                    result = (Collection) ReflectUtils.newInstance(target);
                }
                if (result == null) {
                    // 无法创建集合时返回null；
                    return null;
                }
                if (genericType == null) {
                    // 没有泛型时使用源对象的class进行转换。不在循环中判断，提升一点微乎其微的性能。
                    for (Object sourceValue : sourceValues) {
                        result.add(BeanUtils.copy(sourceValue, sourceValue.getClass()));
                    }
                } else {
                    // 有泛型时转换为泛型对应对象
                    for (Object sourceValue : sourceValues) {
                        result.add(BeanUtils.copy(sourceValue, genericType));
                    }
                }
                return result;

            }

            if (source == target) {
                if (source.isEnum() || isPrimitive(source)) {
                    return value;
                }
                // TODO copy
                return BeanUtils.copy(value, source);
            }
            return null;
        }

        public static boolean isPrimitive(Class<?> clazz) {
            return clazz.isPrimitive() || PRIMITIVES.contains(clazz);
        }
    }

    public interface CustomerConverter<T, R> {
        R convert(T t);
    }

    static List<CustomerConverter<?, ?>> list = new ArrayList<>();

    static {
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
