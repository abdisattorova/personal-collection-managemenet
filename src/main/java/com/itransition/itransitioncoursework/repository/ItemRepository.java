package com.itransition.itransitioncoursework.repository;

import com.itransition.itransitioncoursework.entity.Item;
import com.itransition.itransitioncoursework.projection.ItemProjectionForCollection;
import com.itransition.itransitioncoursework.projection.ItemProjectionForDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID> {


    @Query(nativeQuery = true, value = "select cast(i.id as varchar), " +
            "       i.name                              as name, " +
            "       trim(TO_CHAR(i.created_at, 'dd')) || ' ' || " +
            "       trim(TO_CHAR(i.created_at, 'Month')) || ' ' || " +
            "       trim(TO_CHAR(i.created_at, 'yyyy')) as createdDate " +
            "from items i" +
            " where i.collection_id = :collectionId")
    List<ItemProjectionForCollection> getItemsOfCollection(UUID collectionId);

    @Query(nativeQuery = true, value = "select cast(i.id as varchar), " +
            "       i.name                              as name, " +
            "       trim(TO_CHAR(i.created_at, 'dd')) || ' ' || " +
            "       trim(TO_CHAR(i.created_at, 'Month')) || ' ' || " +
            "       trim(TO_CHAR(i.created_at, 'yyyy')) as createdDate " +
            "from items i")
    List<ItemProjectionForCollection> getAllItems();

    @Query(nativeQuery = true, value = "select cast(i.id as varchar), " +
            "       i.name                              as name, " +
            "       trim(TO_CHAR(i.created_at, 'dd')) || ' ' || " +
            "       trim(TO_CHAR(i.created_at, 'Month')) || ' ' || " +
            "       trim(TO_CHAR(i.created_at, 'yyyy')) as createdDate " +
            "from items i" +
            " order by i.created_at desc " +
            " limit 5")
    List<ItemProjectionForCollection> getLatestItems();


    @Query(nativeQuery = true, value = "select cast(i.id as varchar), " +
            "       i.name                              as name, " +
            "       trim(TO_CHAR(i.created_at, 'dd')) || ' ' || " +
            "       trim(TO_CHAR(i.created_at, 'Month')) || ' ' || " +
            "       trim(TO_CHAR(i.created_at, 'yyyy')) as createdDate, " +
            "       cast(c.id as varchar)    as collectionId, " +
            "       c.name                              as collectionName, " +
            "       cast(u.id as varchar)                  as authorId, " +
            "       concat(u.first_name, ' ', u.last_name) as authorName " +
            "from items i " +
            "         join collections c on c.id = i.collection_id " +
            "         left join users u on u.id = i.created_by_id " +
            "where i.id = :itemId")
    ItemProjectionForDetail getItemDetails(UUID itemId);


    @Query(nativeQuery = true, value = " select count (*) from dislikes" +
            " where item_id = :itemId")
    Integer getDislikesCount(UUID itemId);

    @Query(nativeQuery = true, value = " select count (*) from likes" +
            " where item_id = :itemId")
    Integer getLikesCount(UUID itemId);


    @Query(nativeQuery = true, value = " select cast (id as varchar )from likes" +
            " where user_id = :userId and item_id = :itemId")
    UUID checkIfUserLikesTheItem(UUID userId, UUID itemId);

    @Query(nativeQuery = true, value = " select cast (id as varchar )from dislikes" +
            " where user_id = :userId and item_id = :itemId")
    UUID checkIfUserDislikesTheItem(UUID userId, UUID itemId);


}
