package com.itransition.itransitioncoursework.repository;
//Sevinch Abdisattorova 06/15/2022 11:04 AM


import com.itransition.itransitioncoursework.entity.Role;
import com.itransition.itransitioncoursework.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByEmail(String email);

    User findByEmail(String email);

    @Query(nativeQuery = true,value = " select count(*) from users")
    Integer countAll();


    @Query(nativeQuery = true,value = "select count(*)\n" +
            "from users\n" +
            "         join roles r on r.id = users.role_id\n" +
            "where r.role_enum='USER'")
    Integer countUsers();


    @Query(nativeQuery = true,value = "select count(*)\n" +
            "from users\n" +
            "         join roles r on r.id = users.role_id\n" +
            "where r.role_enum='ADMIN'")
    Integer countAdmins();
}
