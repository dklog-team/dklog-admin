package kr.dklog.admin.dklogadmin.common.crypto;

import org.springframework.stereotype.Component;

@Component
public class SCryptPasswordEncoder {

    private static final org.springframework.security.crypto.scrypt.SCryptPasswordEncoder encoder = new org.springframework.security.crypto.scrypt.SCryptPasswordEncoder(
            16,
            8,
            1,
            32,
            64
    );

    public String encrypt(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    public boolean matches(String rawPassword, String encryptedPassword) {
        return encoder.matches(rawPassword, encryptedPassword);
    }
}
