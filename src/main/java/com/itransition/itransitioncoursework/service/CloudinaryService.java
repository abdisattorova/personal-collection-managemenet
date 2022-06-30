package com.itransition.itransitioncoursework.service;
//Sevinch Abdisattorova 06/19/2022 8:19 AM

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class CloudinaryService {


    @Value("${DEFAULT_PICTURE_URL}")
    private String defaultPicture;


    private final Cloudinary cloudinary;

    public File convert(MultipartFile image) {
        try {
            File file = new File("src/main/resources/file/" + Objects.requireNonNull(
                    image.getOriginalFilename()
            ));
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(image.getBytes());
            fileOutputStream.close();
            return file;
        } catch (Exception e) {
            log.info("cannot convert MultiPartFile to File {}", e.getMessage());
            return null;
        }
    }


    public String uploadImage(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty())
            return defaultPicture;
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        return (String) uploadResult.get("url");

    }


}


