package symmetrictest;

import top.viewv.model.mac.SHA;
import top.viewv.model.symmetric.Decrypt;
import top.viewv.model.symmetric.Encrypt;
import top.viewv.model.tools.GenerateSecKey;
import top.viewv.model.tools.PasswordGenerate;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Base64;

public class EncryptionTest {
    public static void main(String[] args) {
        String currentPath = System.getProperty("user.dir");

        String password = PasswordGenerate.generatePassword(
                2,5,
                4,5,20);
        System.out.println(password);
        System.out.println(password.length());

        SecretKey secretKey = GenerateSecKey.generateKey(password,256,65566,
                1,"AES");

        assert secretKey != null : "key is empty!";
        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        System.out.println(encodedKey);
        byte[] hashcode = new byte[0];

        try {
            hashcode = SHA.digest(currentPath + "/src/test/java/symmetrictest/test.pdf" ,"");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // AES/GCM/NoPadding AES/CBC/PKCS5Padding
        Encrypt.encrypt(currentPath + "/src/test/java/symmetrictest/","test.pdf",
                                    currentPath + "/src/test/java/symmetrictest/testenc.enc",
                                    "ChaCha20-Poly1305",secretKey,true,hashcode);

        Decrypt.decrypt(currentPath + "/src/test/java/symmetrictest/testenc.enc",
                            currentPath + "/src/test/java/", secretKey);
    }
}
