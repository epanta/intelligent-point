package com.epnj.intelligentpoint.api.utils;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtilsTest {
    
    private static final String PASSWORD = "123456";
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Test
    public void testPasswordNull() throws Exception {
        Assert.assertNull(PasswordUtils.generateCrypt(null));
    }

    @Test
    public void testGenerateHashPassword() throws Exception {
        String hash = PasswordUtils.generateCrypt(PASSWORD);
        Assert.assertTrue(bCryptPasswordEncoder.matches(PASSWORD, hash));
    }
    
}
