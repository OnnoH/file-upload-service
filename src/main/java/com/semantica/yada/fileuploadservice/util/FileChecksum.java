package com.semantica.yada.fileuploadservice.util;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Component
public class FileChecksum {

    public static String getChecksum(String filePath) {
        return checksum(filePath);
    }

    private static String checksum(String filePath) {

        MessageDigest messageDigest = null;

        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            DigestInputStream dis = new DigestInputStream(new FileInputStream(filePath), messageDigest);
            while (dis.read() != -1);
            messageDigest = dis.getMessageDigest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bytesToHex(messageDigest.digest());

    }

    private static String bytesToHex(byte[] hashInBytes) {

        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();

    }

}
