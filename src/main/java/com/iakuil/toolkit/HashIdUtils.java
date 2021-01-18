package com.iakuil.toolkit;

import org.apache.commons.lang3.ObjectUtils;
import org.hashids.Hashids;

import java.util.Objects;

/**
 * Hashids工具类
 *
 * @author Kai
 */
public class HashIdUtils {
    private static final String DEFAULT_SALT = "itsADemo4Hash";
    private static final int DEFAULT_HASH_LENGTH = 8;

    /**
     * Returns a salted unique hash of the number.
     *
     * @param plainid the number to hash
     * @return a salted unique hash
     */
    public static String encrypt(Long plainid) {
        return encrypt(plainid, null);
    }

    /**
     * Returns a salted unique hash of the number.
     *
     * @param plainid the number to hash
     * @param salt    the salt of hash
     * @return a salted unique hash
     */
    public static String encrypt(Long plainid, String salt) {
        Objects.requireNonNull(plainid, "Id must not be empty!");
        Hashids hashids = new Hashids(ObjectUtils.defaultIfNull(salt, DEFAULT_SALT), DEFAULT_HASH_LENGTH);
        return hashids.encode(plainid);
    }

    /**
     * Returns a decrypted number
     *
     * @param ciphertext the hash to decrypt
     * @return a decrypted number
     */
    public static Long decrypt(String ciphertext) {
        return decrypt(ciphertext, null);
    }

    /**
     * Returns decrypted number
     *
     * @param ciphertext the hash to decrypt
     * @param salt       the salt of hash
     * @return a decrypted number
     */
    public static Long decrypt(String ciphertext, String salt) {
        Objects.requireNonNull(ciphertext, "Text must not be empty!");
        Hashids hashids = new Hashids(ObjectUtils.defaultIfNull(salt, DEFAULT_SALT), DEFAULT_HASH_LENGTH);
        long[] decoded = hashids.decode(ciphertext);
        return decoded.length > 0 ? decoded[0] : null;
    }
}