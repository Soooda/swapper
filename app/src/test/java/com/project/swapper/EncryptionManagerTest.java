package com.project.swapper;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.project.swapper.security.EncryptionManager;

import java.security.InvalidKeyException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public class EncryptionManagerTest {
    private EncryptionManager manager;

    @Before
    public void setup() {
        try {
            manager = new EncryptionManager();
        } catch (Exception e) {}
    }

    @Test
    public void testConstructor() {
        try {
            new EncryptionManager();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testEncryptNull() {
        try {
            manager.encrypt(null);
        } catch (BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            fail(e.getMessage());
        } catch (NullPointerException e) {}
    }

    @Test
    public void testEncryptEmpty() {
        try {
            String temp = manager.encrypt("");
            assertEquals("", manager.decrypt(temp));
        } catch (BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testEncryptNumbers() {
        String expected = "123321";
        try {
            String temp = manager.encrypt(expected);
            assertEquals(expected , manager.decrypt(temp));
        } catch (BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testEncryptShort() {
        String expected = "123321kxl";
        try {
            String temp = manager.encrypt(expected);
            assertEquals(expected , manager.decrypt(temp));
        } catch (BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testEncryptLong() {
        String expected = "UHTA2RFy*t=YpjuKk!THxKQw";
        try {
            String temp = manager.encrypt(expected);
            assertEquals(expected , manager.decrypt(temp));
        } catch (BadPaddingException | IllegalBlockSizeException | InvalidKeyException e) {
            fail(e.getMessage());
        }
    }
}
