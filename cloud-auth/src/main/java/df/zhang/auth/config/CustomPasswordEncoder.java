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
package df.zhang.auth.config;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

/**
 * 密码加密器，使用{@link DigestUtils}将密码转换为16位长度的MD5字节数组，
 * 然后与预先设置好的SALT数组交叉合并，得到最终32位长度的字节数组，转换为SHA-1字符串。
 *
 * @author df.zhang Email: 84154025@qq.com
 * @date 2019-05-02
 * @since 1.0.0
 */
public class CustomPasswordEncoder implements PasswordEncoder {
    private static final byte[] SALT = DigestUtils.md5("@~df.zhang~@");
    private static final int ENCODE_LEN = 32;
    private static final int JUMP = 2;


    @Override
    public String encode(CharSequence rawPassword) {
        assert Objects.nonNull(rawPassword);
        byte[] rawBytes = DigestUtils.md5(rawPassword.toString());
        byte[] encodeBytes = new byte[ENCODE_LEN];
        // 将两个字节数组交叉组合成一个字节数组，循环中可实现某个数组反转
        for (int i = 0, j = 0; i < ENCODE_LEN; i += JUMP, j++) {
            encodeBytes[i] = rawBytes[j];
            encodeBytes[i + 1] = SALT[j];
        }
        return DigestUtils.sha1Hex(encodeBytes);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }
}
