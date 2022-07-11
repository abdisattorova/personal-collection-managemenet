package com.itransition.itransitioncoursework.repository;

import com.itransition.itransitioncoursework.entity.Collection;
import com.itransition.itransitioncoursework.projection.CollectionDetailsProjection;
import com.itransition.itransitioncoursework.projection.CollectionProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CollectionRepository
        extends JpaRepository<Collection, UUID> {

    @Query(nativeQuery = true, value = "select cast(c.id as varchar) as id," +
            "       c.name                as name," +
            "       c.image_url           as imageUrl," +
            "       count(i.*)            as itemsCount " +
            "from collections c " +
            "       left  join items i on c.id = i.collection_id " +
            "group by c.id " +
            "order by itemsCount desc " +
            "limit 5; ")
    List<CollectionProjection> getTopCollections();


    @Query(nativeQuery = true, value = "select cast(c.id as varchar) as id," +
            "       c.name                as name," +
            "       c.image_url           as imageUrl," +
            "       count(i.*)            as itemsCount " +
            "from collections c " +
            "       left  join items i on c.id = i.collection_id " +
            "group by c.id " +
            "order by itemsCount")
    List<CollectionProjection> getAllCollections();


    @Query(nativeQuery = true, value = "select cast(c.id as varchar)  as id," +
            "       c.name                                 as name, " +
            "       c.description                          as description, " +
            "       c.image_url                            as imageUrl, " +
            "       cast(c.topic_id as varchar )           as topicId, " +
            "       t.name                                 as topicName, " +
            "       count(i.*)                             as itemsCount, " +
            "       cast(u.id as varchar)                  as authorId, " +
            "       concat(u.first_name, ' ', u.last_name) as authorName " +
            "from collections c " +
            "         join topics t on c.topic_id = t.id " +
            "         left join items i on c.id = i.collection_id " +
            "         left join users u on u.id = c.created_by_id " +
            "where c.id = :collectionId " +
            "group by cast(c.id as varchar), " +
            "         c.name, c.description, " +
            "         c.image_url, c.topic_id, " +
            "         t.name, c.created_at, u.last_name, u.id " +
            "order by itemsCount desc; ")
    CollectionDetailsProjection getDetailsOfCollection(UUID collectionId);


    @Query(nativeQuery = true, value = "select " +
            "       trim(TO_CHAR(c.created_at, 'dd')) || ' ' || " +
            "       trim(TO_CHAR(c.created_at, 'Month')) || ' ' || " +
            "       trim(TO_CHAR(c.created_at, 'yyyy')) as createdDate " +
            "from collections c " +
            "where c.id=:collectionId ")
    String getCreatedDateOfCollection(UUID collectionId);


    @Query(nativeQuery = true, value = " select count(*) from collections")
    Integer countAll();


    @Query(nativeQuery = true, value = "select cast(c.id as varchar) as id," +
            "       c.name                as name," +
            "       c.image_url           as imageUrl," +
            "       count(i.*)            as itemsCount " +
            "from collections c " +
            "       left  join items i on c.id = i.collection_id " +
            "where c.created_by_id = :userId " +
            "group by c.id " +
            "order by itemsCount")
    List<CollectionProjection> getMyCollections(UUID userId);

    void deleteAllByCreatedBy(UUID id);
}
