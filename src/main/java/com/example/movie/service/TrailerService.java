package com.example.movie.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class TrailerService {

    private static final String VIDEO_ROOT =
            "D:/safaa-abdelsattar-projects/trailers/";

    public String uploadTrailer(MultipartFile trailer) throws IOException {

        File directory = new File(VIDEO_ROOT);
        if (!directory.exists()) {
            directory.mkdirs(); // ✅ create folders if missing
        }

        String fileName = System.currentTimeMillis() + "_" + trailer.getOriginalFilename();
        File destination = new File(VIDEO_ROOT + fileName);

        trailer.transferTo(destination);

        return fileName;
    }
}

