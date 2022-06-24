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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CollectionService {


    private final TopicRepository topicRepository;

    private final Cloudinary cloudinary;

    private final CollectionRepository collectionRepository;

    private final CustomFieldRepository customFieldRepository;


    public void saveCollection(CollectionDto collectionDto, MultipartFile image) {

        Collection collection = new Collection();
        collection.setName(collectionDto.getName());
        collection.setDescription(collectionDto.getDescription());

        try {
            File uploadedFile = convertMultiPartToFile(image);
            Map uploadResult = cloudinary.uploader().upload(uploadedFile, ObjectUtils.emptyMap());
            String url = uploadResult.get("url").toString();
            collection.setImageUrl(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Optional<Topic> topic = topicRepository.findById(collectionDto.getTopicId());
        collection.setTopic(topic.get());

        collectionRepository.save(collection);

        List<CustomFieldDto> customFieldDtos = collectionDto.getCustomFields();

       List<Integer> integers = new ArrayList<>();

        long count = integers.stream().filter(integer -> integer==8).count();

        for (CustomFieldDto customFieldDto : customFieldDtos) {
            customFieldRepository.save(new CustomField(customFieldDto.getFieldName(),
                    FieldDataType.valueOf(customFieldDto.getFieldDataType())
                    , collection));
        }

    }


    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File("src/main/resources/file/" + file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

}


