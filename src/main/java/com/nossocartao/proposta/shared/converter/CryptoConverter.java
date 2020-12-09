package com.nossocartao.proposta.shared.converter;

import org.springframework.beans.factory.annotation.Value;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.security.Key;
import java.util.Base64;

@Converter
public class CryptoConverter implements AttributeConverter<String, String> {
    private static final String ALGORITHM = "AES";

    @Value("${crypto.secret}")
    private String secret;

    @Override
    public String convertToDatabaseColumn(String text) {
        Key key = new SecretKeySpec(this.secret.getBytes(), ALGORITHM);

        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);

            return Base64.getEncoder().encodeToString(cipher.doFinal(text.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String convertToEntityAttribute(String textEncrypted) {
        Key key = new SecretKeySpec(this.secret.getBytes(), ALGORITHM);

        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);

            return new String(cipher.doFinal(Base64.getDecoder().decode(textEncrypted)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
