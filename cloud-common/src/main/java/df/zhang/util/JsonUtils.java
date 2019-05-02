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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import df.zhang.util.date.DatePattern;
import lombok.NonNull;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

/**
 * JSON序列化或反序列化工具，使用{@link ObjectMapper}实现。
 *
 * @author df.zhang Email: 84154025@qq.com
 * @version 1.0.0
 * @date 2019-04-21
 */
public final class JsonUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        OBJECT_MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DatePattern.DATETIME_YYYY_MM_DD_HH_MM_SS));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DatePattern.DATETIME_YYYY_MM_DD_HH_MM_SS));
        javaTimeModule.addSerializer(Date.class, new DateSerializer(false, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addDeserializer(Date.class, new JsonDeserializer<Date>() {

            @Override
            public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                String dateString = jsonParser.getText().trim();
                if (StringUtils.isNotBlank(dateString)) {
                    return Date.from(LocalDateTime.parse(dateString).atZone(ZoneId.systemDefault()).toInstant());
                }
                return null;
            }
        });
        javaTimeModule.addSerializer(Instant.class, new JsonSerializer<Instant>() {

            @Override
            public void serialize(Instant instant, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                if (instant == null) {
                    return;
                }
                jsonGenerator.writeString(DatePattern.DATETIME_YYYY_MM_DD_HH_MM_SS.format(instant.atZone(ZoneId.systemDefault())));
            }
        });
        javaTimeModule.addDeserializer(Instant.class, new JsonDeserializer<Instant>() {

            @Override
            public Instant deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                String dateString = jsonParser.getText().trim();
                if (StringUtils.isNotBlank(dateString)) {
                    return LocalDateTime.parse(dateString).atZone(ZoneId.systemDefault()).toInstant();
                }
                return null;
            }
        });

        OBJECT_MAPPER.registerModule(new ParameterNamesModule());
        OBJECT_MAPPER.registerModule(new Jdk8Module());
        OBJECT_MAPPER.registerModule(javaTimeModule);
    }

    /**
     * 将任意对象序列化为JSON字符串，具体输出内容由当前类{@link ObjectMapper}的配置决定。详情参考类说明。
     *
     * @param obj 任意对象。
     * @return java.lang.String
     * @date 2019-04-21 21:59
     * @author df.zhang
     * @since 1.0.0
     */
    public static String serialize(@NonNull Object obj) {
        if (Objects.isNull(obj)) {
            return "{}";
        }
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            /*
             * 如果序列化失败，判断obj的类型。 是数组或集合就输出[]，否则输出{}。
             */
            Class<?> objClass = obj.getClass();
            if (objClass.isArray() || Collection.class.isAssignableFrom(objClass)) {
                return "[]";
            }
            return "{}";
        }
    }

    /**
     * 将字符串反序列化为指定类型的对象。要求第一个参数JSON字符串是对象类型字符串，即以{开头，以}结尾；第二个参数的类型不为抽象类、不为接口、有无参构造函数。
     * 若需要将[]数组类型json字符串反序列化为集合（含泛型）或更复杂的数组（集合）对象，请使用{@link JsonUtils#deserialize(String, TypeReference)}方法。
     *
     * @param json   JSON字符串
     * @param tClass 不为抽象类、不为接口、有无参构造函数的类型
     * @return java.util.Optional&lt;T&gt; 返回类型使用{@link Optional}包装，有效避免空指针异常
     * @date 2019-04-21 22:35
     * @author df.zhang
     * @since 1.0.0
     */
    public static <T> Optional<T> deserialize(@NonNull String json, @NonNull Class<T> tClass) {
        if (StringUtils.isBlank(json) || isDeserializeClass(tClass)) {
            return Optional.empty();
        }
        try {
            return Optional.ofNullable(OBJECT_MAPPER.readValue(json, tClass));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    /**
     * 将字符串反序列化为指定类型的对象。允许第一个参数JSON字符串任意对象类型或数组类型字符串，即以“{”开头“}”结尾或“[”开头“]”结尾均可；
     * 第二个参数的类型需要为{@link TypeReference}的实现类，该类可以接收复杂的泛型参数，用于在编译时获取到准确的泛型类型。。
     *
     * @param json          JSON字符串
     * @param typeReference 任意{@link TypeReference}的实现类，该类可以接收复杂的泛型参数，用于在编译时获取到准确的泛型类型。
     * @return java.util.Optional&lt;T&gt; 返回类型使用{@link Optional}包装，有效避免空指针异常
     * @date 2019-04-21 22:41
     * @author df.zhang
     * @since 1.0.0
     */
    public static <T> Optional<T> deserialize(String json, TypeReference typeReference) {
        if (StringUtils.isBlank(json)) {
            return Optional.empty();
        }
        try {
            return Optional.ofNullable(OBJECT_MAPPER.readValue(json, typeReference));
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    /**
     * 检查传入任意类型Class是否满足反序列化要求，即不为抽象类、不为接口、有无参构造函数
     *
     * @param clazz param1
     * @return boolean
     * @date 2019-04-21 22:08
     * @author df.zhang
     * @since 1.0.0
     */
    private static boolean isDeserializeClass(Class<?> clazz) {
        if (Objects.isNull(clazz)) {
            return false;
        }
        // 判断是否为抽象类或者接口
        int mod = clazz.getModifiers();
        if (Modifier.isAbstract(mod) || Modifier.isInterface(mod)) {
            return false;
        }
        // 获取无参构造函数
        try {
            Constructor<?> constructor = clazz.getConstructor();
            return constructor != null;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }


    private JsonUtils() {
        throw new UnsupportedOperationException();
    }
}
