package com.itransition.itransitioncoursework.payload;

import com.itransition.itransitioncoursework.entity.Role;
import com.itransition.itransitioncoursework.entity.Topic;
import com.itransition.itransitioncoursework.entity.User;
import com.itransition.itransitioncoursework.entity.enums.RoleEnum;
import com.itransition.itransitioncoursework.repository.TopicRepository;
import com.itransition.itransitioncoursework.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final TopicRepository topicRepository;

    @org.springframework.beans.factory.annotation.Value("${spring.sql.init.mode}")
    String initMode;

    @Override
    public void run(String... args) throws Exception {
        if (initMode.equals("always")) {

            /**
             * SAVE USER
             */
            userRepository.save(new User("Sevinch",
                    "Abdisattarova",
                    "abdisattarovasevinch5@gmail.com",
                    encoder.encode("1"),
                    false,
                    new Role(RoleEnum.ADMIN)
            ));

            userRepository.save(new User("Zuhra",
                    "Abdurasulova",
                    "zuhra@gmail.com",
                    encoder.encode("1"),
                    false,
                    new Role(RoleEnum.USER)));

            userRepository.save(new User("Dilsabo",
                    "Eshpulatova",
                    "dilsabo@gmail.com",
                    encoder.encode("1"),
                    false,
                    new Role(RoleEnum.USER)));

            userRepository.save(new User("Bekzod",
                    "Ganiyev",
                    "bekzod@gmail.com",
                    encoder.encode("1"),
                    false,
                    new Role(RoleEnum.USER)));

            userRepository.save(new User("Zuhriddin",
                    "Bahriddinov",
                    "zukich@gmail.com",
                    encoder.encode("1"),
                    false,
                    new Role(RoleEnum.USER)));

            userRepository.save(new User("John",
                    "Terry",
                    "terry@gmail.com",
                    encoder.encode("1"),
                    false,
                    new Role(RoleEnum.USER)));

            userRepository.save(new User("Asilbek",
                    "Abdisattorov",
                    "asilbek@gmail.com",
                    encoder.encode("1"),
                    false,
                    new Role(RoleEnum.USER)));

            userRepository.save(new User("Javhar",
                    "Abdusattorov",
                    "javhar@gmail.com",
                    encoder.encode("1"),
                    false,
                    new Role(RoleEnum.USER)));


            userRepository.save(new User("Shohjahon",
                    "Abdisatorov",
                    "shohjahon@gmail.com",
                    encoder.encode("1"),
                    false,
                    new Role(RoleEnum.USER)));

            topicRepository.save(new Topic("Books"));
            topicRepository.save(new Topic("Electronics"));
            topicRepository.save(new Topic("Coins"));
            topicRepository.save(new Topic("Photos"));
        }
    }
}
