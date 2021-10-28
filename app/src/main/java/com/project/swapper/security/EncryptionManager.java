package com.project.swapper.security;

import android.provider.Settings;
import android.util.Base64;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * This class handles both encryption and decryption of text.
 * Ref. https://stackoverflow.com/a/34098587
 */
public class EncryptionManager {
    // AES cipher
    private Cipher cipher;
    // Key
    private SecretKeySpec secret;

    public EncryptionManager() throws NoSuchPaddingException, NoSuchAlgorithmException {
        String id = Settings.Secure.ANDROID_ID;
        // Padding
        while (id.length() != 16) {
            id = id + " ";
        }
        this.secret = new SecretKeySpec(id.getBytes(StandardCharsets.UTF_8), "AES");
        this.cipher = Cipher.getInstance("AES");
    }

    /**
     * Encrypts a given string using AES.
     * @param plainText The input string
     * @return Encrypted text.
     * @throws InvalidKeyException Invalid Key used for encryption!
     * @throws BadPaddingException Input string padded incorrectly!
     * @throws IllegalBlockSizeException Block size is illegal!
     */
    public String encrypt(String plainText) throws InvalidKeyException, BadPaddingException,
            IllegalBlockSizeException {
        byte[] plainTextByte = plainText.getBytes();
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        byte[] encryptedByte = cipher.doFinal(plainTextByte);
        String encryptedText = Base64.encodeToString(encryptedByte, Base64.DEFAULT);
        return encryptedText;
    }

    /**
     * Decrypts a given encrypted text.
     * @param encryptedText The input string
     * @return Decrypted text.
     * @throws InvalidKeyException Invalid Key used for encryption!
     * @throws IllegalBlockSizeException Block size is illegal!
     * @throws BadPaddingException Input string padded incorrectly!
     */
    public String decrypt(String encryptedText) throws InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        byte[] encryptedByte = Base64.decode(encryptedText, Base64.DEFAULT);
        cipher.init(Cipher.DECRYPT_MODE, secret);
        byte[] decryptedByte = cipher.doFinal(encryptedByte);
        String text = new String(decryptedByte);
        return text;
    }
}
