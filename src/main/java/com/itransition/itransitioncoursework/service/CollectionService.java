package com.itransition.itransitioncoursework.service;
//Sevinch Abdisattorova 06/19/2022 8:19 AM

import com.itransition.itransitioncoursework.dto.CollectionDto;
import com.itransition.itransitioncoursework.dto.CustomFieldDto;
import com.itransition.itransitioncoursework.entity.*;
import com.itransition.itransitioncoursework.projection.CollectionDetailsProjection;
import com.itransition.itransitioncoursework.projection.CollectionProjection;
import com.itransition.itransitioncoursework.repository.CollectionRepository;
import com.itransition.itransitioncoursework.repository.CustomFieldRepository;
import com.itransition.itransitioncoursework.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CollectionService {


    private final TopicRepository topicRepository;

    private final CloudinaryService cloudinaryService;

    private final CollectionRepository collectionRepository;

    private final CustomFieldRepository customFieldRepository;


    public void saveCollection(CollectionDto collectionDto,
                               MultipartFile image) throws IOException {

        Collection collection = new Collection();
        collection.setName(collectionDto.getName());
        collection.setDescription(collectionDto.getDescription());

        collection.setImageUrl(cloudinaryService.uploadImage(image));

        Optional<Topic> topic = topicRepository.findById(collectionDto.getTopicId());

        topic.ifPresent(collection::setTopic);

        collectionRepository.save(collection);

        List<CustomFieldDto> customFieldDtos = collectionDto.getCustomFields();

        for (CustomFieldDto customFieldDto : customFieldDtos) {
            customFieldRepository.save(new CustomField(customFieldDto.getFieldName(),
                    FieldDataType.valueOf(customFieldDto.getFieldDataType())
                    , collection));
        }

    }


    public List<CollectionProjection> getTopCollections(Model model) {
        return collectionRepository.getTopCollections();
    }


    public Collection getCollectionById(UUID id) {
        return collectionRepository.getById(id);
    }


    public String getDetailsOfCollection(UUID collectionId, Model model) {
        CollectionDetailsProjection detailsOfCollection = collectionRepository.getDetailsOfCollection(collectionId);
        model.addAttribute("collection", detailsOfCollection);
        return "collection-detail";
    }


    public Integer getCountOfAllCollections() {
        return collectionRepository.countAll();
    }


    public String getAllCollections(Model model) {
        List<CollectionProjection> collections = collectionRepository.getAllCollections();
        model.addAttribute("collections", collections);
        return "collections";
    }

    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public String getMyCollections(Model model, User user) {
        List<CollectionProjection> collections = collectionRepository.getMyCollections(user.getId());
        model.addAttribute("collections", collections);
        return "collections";
    }
}


