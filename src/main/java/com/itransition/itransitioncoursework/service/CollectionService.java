package com.itransition.itransitioncoursework.service;
//Sevinch Abdisattorova 06/19/2022 8:19 AM

import com.itransition.itransitioncoursework.dto.CollectionDto;
import com.itransition.itransitioncoursework.dto.CustomFieldDto;
import com.itransition.itransitioncoursework.entity.*;
import com.itransition.itransitioncoursework.projection.CollectionDetailsProjection;
import com.itransition.itransitioncoursework.projection.CollectionProjection;
import com.itransition.itransitioncoursework.projection.ItemProjectionForCollection;
import com.itransition.itransitioncoursework.repository.CollectionRepository;
import com.itransition.itransitioncoursework.repository.CustomFieldRepository;
import com.itransition.itransitioncoursework.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CollectionService {


    private final TopicRepository topicRepository;
    private final CloudinaryService cloudinaryService;
    private final CollectionRepository collectionRepository;
    private final CustomFieldRepository customFieldRepository;
    private final EntityManager entityManager;
    private final ItemService itemService;
    private final CommentService commentService;


    public String getMainPage(Model model, String text) {
        if (text == null) {
            List<ItemProjectionForCollection> items = itemService.getLastAddedItems();
            model.addAttribute("latestItems", items);
            List<CollectionProjection> topCollections = getTopCollections(model);
            model.addAttribute("collections", topCollections);
            return "index";
        } else {
            return searchItemsAndCollectionsByText(text, model);
        }
    }


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


    public String getDetailsOfCollection(UUID collectionId,
                                         Model model) {
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


    public List<Collection> collectionsBySearch(String text,
                                                FullTextEntityManager fullTextEntityManager) {
        QueryBuilder queryBuilder2 = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Collection.class)
                .get();

        org.apache.lucene.search.Query query2 = queryBuilder2
                .keyword()
                .wildcard()
                .onFields("name", "description")
                .matching("*" + text + "*")
                .createQuery();

        org.hibernate.search.jpa.FullTextQuery jpaQuery2
                = fullTextEntityManager.createFullTextQuery(query2,
                Collection.class);
        return jpaQuery2.getResultList();
    }


    public String searchItemsAndCollectionsByText(String text, Model model) {

        FullTextEntityManager fullTextEntityManager
                = Search.getFullTextEntityManager(entityManager);

        List<Item> itemsBySearch = itemService.getItemsBySearch(text,
                fullTextEntityManager);
        model.addAttribute("items", itemsBySearch);

        List<Collection> collections = collectionsBySearch(text,
                fullTextEntityManager);
        model.addAttribute("collections", collections);

        List<Comment> comments = commentService.getCommentsBySearch(text, fullTextEntityManager);
        model.addAttribute("comments", comments);
        for (Comment comment : comments) {
            System.out.println(comment.getContent());
        }
        return "main-page";
    }

    public String deleteCollection(UUID collectionId, RedirectAttributes attributes) {
        Optional<Collection> byId = collectionRepository.findById(collectionId);
        if (byId.isPresent()) {
            try {
                collectionRepository.deleteById(collectionId);
                attributes.addFlashAttribute("success", true);
                attributes.addFlashAttribute("message", "Collection successfully deleted!");
                return "redirect:/";
            } catch (Exception e) {
                attributes.addFlashAttribute("success", false);
                attributes.addFlashAttribute("message", "Collection not deleted!");
                return "redirect:/";
            }
        }
        attributes.addFlashAttribute("success", false);
        attributes.addFlashAttribute("message", "Collection not found!");
        return "redirect:/";
    }
}


