package com.semantica.yada.fileuploadservice.util;

import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FileMimeType {

    public static String getMimeType(File file) {

        String mimeType = "application/octet-stream";

        try {
            Tika tika = new Tika();
            mimeType = tika.detect(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mimeType;

    }
}
