package com.itransition.itransitioncoursework.service;
//Sevinch Abdisattorova 06/19/2022 8:19 AM

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.itransition.itransitioncoursework.dto.CollectionDto;
import com.itransition.itransitioncoursework.dto.CustomFieldDto;
import com.itransition.itransitioncoursework.entity.Collection;
import com.itransition.itransitioncoursework.entity.CustomField;
import com.itransition.itransitioncoursework.entity.FieldDataType;
import com.itransition.itransitioncoursework.entity.Topic;
import com.itransition.itransitioncoursework.repository.CollectionRepository;
import com.itransition.itransitioncoursework.repository.CustomFieldRepository;
import com.itransition.itransitioncoursework.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class CollectionService {


    private final TopicRepository topicRepository;

    @Autowired
    Cloudinary cloudinary;

    private final CollectionRepository collectionRepository;

    private final CustomFieldRepository customFieldRepository;


    public void saveCollection(CollectionDto collectionDto, MultipartFile image) throws IOException {

        Collection collection = new Collection();
        collection.setName(collectionDto.getName());
        collection.setDescription(collectionDto.getDescription());

        collection.setImageUrl(uploadImage(image));

        Optional<Topic> topic = topicRepository.findById(collectionDto.getTopicId());
        collection.setTopic(topic.get());

        collectionRepository.save(collection);

        List<CustomFieldDto> customFieldDtos = collectionDto.getCustomFields();

        List<Integer> integers = new ArrayList<>();

        long count = integers.stream().filter(integer -> integer == 8).count();

        for (CustomFieldDto customFieldDto : customFieldDtos) {
            customFieldRepository.save(new CustomField(customFieldDto.getFieldName(),
                    FieldDataType.valueOf(customFieldDto.getFieldDataType())
                    , collection));
        }

    }


    private File convert(MultipartFile image) {
        try {
            File file = new File(Objects.requireNonNull(
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

    private String uploadImage(MultipartFile file) throws IOException {
        if (file.isEmpty())
            return "https://efectocolibri.com/wp-content/uploads/2021/01/placeholder.png";
        File image = convert(file);
        Map uploadResult = cloudinary.uploader().upload(image, ObjectUtils.emptyMap());
        return (String) uploadResult.get("url");

    }
}


