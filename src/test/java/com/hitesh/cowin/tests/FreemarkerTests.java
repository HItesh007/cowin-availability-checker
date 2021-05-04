package com.hitesh.cowin.tests;

import com.hitesh.cowin.email.EmailConstants;
import com.hitesh.cowin.utility.AESUtility;
import org.testng.annotations.Test;

public class FreemarkerTests {

    @Test
    public void createAESTokenTest() {
        String secretKey = EmailConstants.EMAIL_SECRET;
        String salt = EmailConstants.EMAIL_SALT;
        String password = "EnterPasswordToEncryptHere";

        // Encrypt Password
        String encryptedPassword = AESUtility.encrypt(password, secretKey, salt);

        // Decrypt Password
        String decryptedPassword = AESUtility.decrypt(encryptedPassword, secretKey, salt);

        System.out.println("Encrypted Password/Email Token : " + encryptedPassword);
        System.out.println("Decrypted Password : " + decryptedPassword);
    }
}
