package ru.vsu.netcracker.parking.frontend.security;

import org.springframework.security.crypto.codec.Utf8;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.util.EncodingUtils;

import java.util.Base64;

public class CustomSHA256PasswordEncoder implements PasswordEncoder {

    private final Digester digester;
    private final BytesKeyGenerator saltGenerator;

    public CustomSHA256PasswordEncoder() {
        this.digester = new Digester("SHA-256", 1024);
        this.saltGenerator = KeyGenerators.secureRandom();
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return this.encode(rawPassword, this.saltGenerator.generateKey());
    }

    public String encode(CharSequence rawPassword, byte[] salt) {
        byte[] digest = this.digest(rawPassword, salt);
        return Base64.getEncoder().encodeToString(digest);
    }

    public byte[] digest(CharSequence rawPassword, byte[] salt) {
        byte[] digest = digester.digest(EncodingUtils.concatenate(new byte[][]{salt, Utf8.encode(rawPassword)}));
        return EncodingUtils.concatenate(new byte[][]{salt, digest});
    }

    public byte[] decode(String encodedPassword) {
        return Base64.getDecoder().decode(encodedPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        byte[] digested = this.decode(encodedPassword);
        byte[] salt = EncodingUtils.subArray(digested, 0, this.saltGenerator.getKeyLength());
        return this.matches(digested, this.digest(rawPassword, salt));
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword, byte[] salt) {
        byte[] digested = this.decode(encodedPassword);
        return this.matches(digested, this.digest(rawPassword, salt));
    }

    public boolean matches(byte[] expected, byte[] actual) {
        if (expected.length != actual.length) {
            return false;
        } else {
            int result = 0;
            for(int i = 0; i < expected.length; ++i) {
                result |= expected[i] ^ actual[i];
            }
            return result == 0;
        }
    }


}
