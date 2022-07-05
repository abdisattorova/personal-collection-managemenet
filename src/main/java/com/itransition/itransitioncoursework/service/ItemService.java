package com.itransition.itransitioncoursework.service;
//Sevinch Abdisattorova 06/26/2022 11:54 PM


import com.itransition.itransitioncoursework.dto.ItemDto;
import com.itransition.itransitioncoursework.entity.Collection;
import com.itransition.itransitioncoursework.entity.*;
import com.itransition.itransitioncoursework.projection.CustomFieldProjection;
import com.itransition.itransitioncoursework.projection.ItemProjectionForCollection;
import com.itransition.itransitioncoursework.projection.ItemProjectionForDetail;
import com.itransition.itransitioncoursework.repository.DislikeRepository;
import com.itransition.itransitioncoursework.repository.FieldValueRepository;
import com.itransition.itransitioncoursework.repository.ItemRepository;
import com.itransition.itransitioncoursework.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemService {

    private final TagService tagService;

    private final CloudinaryService cloudinaryService;

    private final CollectionService collectionService;

    private final CustomFieldsService customFieldsService;

    private final FieldValueRepository fieldValueRepository;

    private final ItemRepository itemRepository;

    private final DislikeRepository dislikeRepository;

    private final LikeRepository likeRepository;

    private final CommentService commentService;


    public String getItemsForm(UUID collectionId, Model model) {
        List<Tag> tags = tagService.getAll();
        model.addAttribute("tags", tags);

        List<CustomFieldProjection> customFields = customFieldsService.getCustomFieldsOfCollection(collectionId);
        model.addAttribute("customFields", customFields);
        ItemDto itemDto = new ItemDto();
        itemDto.setCollectionId(collectionId);
        model.addAttribute("item", itemDto);
        return "item-form";

    }


    public String getEditForm(UUID itemId, Model model) {

        Optional<Item> byId = itemRepository.findById(itemId);
        Item item = byId.get();

        ItemDto itemDto = new ItemDto(item.getCollection().getId(), item.getName(), new ArrayList<>());
        model.addAttribute("item", itemDto);

        List<Tag> tags = tagService.getAll();
        model.addAttribute("tags", tags);

        List<CustomFieldProjection> customFields = customFieldsService
                .getCustomFieldsOfCollection(item.getCollection().getId());

        model.addAttribute("customFields", customFields);
        return "item-form";

    }


    public String addItem(ItemDto itemDto,
                          MultipartHttpServletRequest file,
                          HttpServletRequest request,
                          RedirectAttributes redirectAttributes) throws IOException {

        Collection collectionById = collectionService.getCollectionById(itemDto.getCollectionId());

        List<Tag> tags = new ArrayList<>();

        for (UUID tagId : itemDto.getTagIds()) {
            tags.add(tagService.getTagById(tagId));
        }

        Item item = new Item(itemDto.getName(), collectionById, tags);
        itemRepository.save(item);

        saveFieldValues(item, request, file, itemDto.getCollectionId());
        // TODO: 06/27/2022 success url
        return "redirect:/";
    }


    private void saveFieldValues(Item item,
                                 HttpServletRequest request,
                                 MultipartHttpServletRequest file,
                                 UUID collectionId) throws IOException {

        Map<String, String[]> parameterMap = request.getParameterMap();

        List<CustomFieldProjection> fields = customFieldsService.getCustomFieldsOfCollection(collectionId);


        for (CustomFieldProjection field : fields) {
            if (field.getFieldDataType().equals("file")) {
                CustomField customFieldById = customFieldsService.getCustomFieldById(field.getId());
                MultipartFile file1 = file.getFile(field.getId().toString());
                String url = cloudinaryService.uploadImage(file1);
                FieldValue fieldValue = new FieldValue(url, item, customFieldById);
                fieldValueRepository.save(fieldValue);
            }
        }

        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            try {
                if (entry.getKey() != "name" && entry.getKey() != "tagIds" && entry.getKey() != "collectionId") {
                    UUID key = UUID.fromString(entry.getKey());
                    CustomField customFieldById = customFieldsService.getCustomFieldById(key);
                    if (customFieldById != null) {
                        FieldValue fieldValue = new FieldValue(entry.getValue()[0], item, customFieldById);
                        fieldValueRepository.save(fieldValue);
                    }
                }
            } catch (Exception e) {
//                e.printStackTrace();
            }
        }
    }


    public List<ItemProjectionForCollection> getLastAddedItems() {
        List<ItemProjectionForCollection> latestItems = itemRepository.getLatestItems();
        return latestItems;
    }


    public String likeItem(User user,
                           UUID itemId) {

        UUID uuid = itemRepository.checkIfUserDislikesTheItem(user.getId(), itemId);
        if (uuid != null) {
            dislikeRepository.deleteById(uuid);
        }
        UUID uuid1 = itemRepository.checkIfUserLikesTheItem(user.getId(), itemId);
        if (uuid1 == null) {
            likeRepository.save(new Like(user, itemRepository.findById(itemId).get()));
        }
        return "redirect:/item/details/" + itemId;

    }


    public String disLikeItem(User user,
                              UUID itemId) {

        UUID uuid = itemRepository.checkIfUserLikesTheItem(user.getId(), itemId);
        if (uuid != null) {
            likeRepository.deleteById(uuid);
        }
        UUID uuid1 = itemRepository.checkIfUserDislikesTheItem(user.getId(), itemId);
        if (uuid1 == null) {
            dislikeRepository.save(new Dislike(user, itemRepository.findById(itemId).get()));
        }
        return "redirect:/item/details/" + itemId;
    }


    public String getItemDetails(UUID itemId, Model model) {

        ItemProjectionForDetail itemDetails = itemRepository.getItemDetails(itemId);
        model.addAttribute("item", itemDetails);

        List<ItemProjectionForCollection> relatedItems = itemRepository.getItemsOfCollection(itemDetails.getCollectionId());
        model.addAttribute("relatedItems", relatedItems);
        return "item-detail";
    }

    public String getAllItems(Model model) {
        List<ItemProjectionForCollection> items = itemRepository.getAllItems();
        model.addAttribute("items", items);
        return "items";
    }

    public String deleteItem(UUID id, UUID collectionId, RedirectAttributes model) {
        try {
            itemRepository.deleteById(id);
            model.addFlashAttribute("success", true);
            model.addAttribute("message", "Item successfully  deleted!");
            return "redirect:/collection/details/" + collectionId;
        } catch (Exception e) {
            model.addFlashAttribute("success", false);
            model.addAttribute("message", "Item could not be  deleted!");
            return "redirect:/item/details/" + id;
        }
    }
}
