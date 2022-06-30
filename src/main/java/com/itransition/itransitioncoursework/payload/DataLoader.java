package com.itransition.itransitioncoursework.payload;

import com.itransition.itransitioncoursework.entity.*;
import com.itransition.itransitioncoursework.entity.enums.RoleEnum;
import com.itransition.itransitioncoursework.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final CollectionRepository collectionRepository;
    private final CustomFieldRepository customFieldRepository;
    private final TopicRepository topicRepository;
    private final TagRepository tagRepository;

    @org.springframework.beans.factory.annotation.Value("${spring.sql.init.mode}")
    String initMode;

    @Override
    public void run(String... args) throws Exception {
        if (initMode.equals("always")) {

            /*
             * SAVE USER
             */



            User user = userRepository.save(new User("Sevinch",
                    "Abdisattarova",
                    "abdisattarovasevinch5@gmail.com",
                    encoder.encode("1"),
                    false,
                    new Role(RoleEnum.ADMIN)
            ));
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user,
                    null,
                    user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            {
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
            }

            /* SAVING TOPIC */

            Topic books = topicRepository.save(new Topic("Books"));
            Topic electronics = topicRepository.save(new Topic("Electronics"));
            Topic coins = topicRepository.save(new Topic("Coins"));
            Topic photos = topicRepository.save(new Topic("Photos"));
            Topic flowers = topicRepository.save(new Topic("Flowers"));
            Topic programming = topicRepository.save(new Topic("Programming"));
            Topic laptops = topicRepository.save(new Topic("Laptops"));
            Topic foods = topicRepository.save(new Topic("Foods"));

            /* SAVING TAGS*/
            {
                tagRepository.save(new Tag("Books"));
                tagRepository.save(new Tag("History"));
                tagRepository.save(new Tag("Sport"));
                tagRepository.save(new Tag("Photos"));
                tagRepository.save(new Tag("Nature"));
                tagRepository.save(new Tag("Family"));
                tagRepository.save(new Tag("Friends"));
                tagRepository.save(new Tag("Education"));
            }

            /* SAVING COLLECTIONS */

            {
                /* Flowers collection */
                Collection flowersC = collectionRepository.save(new Collection("Flowers",
                        "Here I collect flowers 🌼",
                        "https://res.cloudinary.com/collections-uz/image/upload/v1656388378/e6cmbtgodhaizn3g3qlq.jpg",
                        flowers
                ));

                customFieldRepository.save(new CustomField("Description",
                        FieldDataType.textarea,
                        flowersC));


                /* Laptops collection */
                Collection laptopsC = collectionRepository.save(new Collection("Laptops",
                        "My laptop collection",
                        "https://res.cloudinary.com/collections-uz/image/upload/v1656396779/laptops-lowres-2x1-_enfvgm.webp",
                        laptops));
                customFieldRepository.save(new CustomField("Description",
                        FieldDataType.textarea,
                        laptopsC));
                customFieldRepository.save(new CustomField("Brand",
                        FieldDataType.text,
                        laptopsC));

                customFieldRepository.save(new CustomField("RAM in gb",
                        FieldDataType.number,
                        laptopsC));

                customFieldRepository.save(new CustomField("Video card in gb",
                        FieldDataType.number,
                        laptopsC));

                customFieldRepository.save(new CustomField("Color",
                        FieldDataType.text,
                        laptopsC));

                customFieldRepository.save(new CustomField("Screen size",
                        FieldDataType.text,
                        laptopsC));

                customFieldRepository.save(new CustomField("Photo",
                        FieldDataType.file,
                        laptopsC));

                /* Saving food collection */

                Collection food = collectionRepository.save(new Collection("Food collection",
                        "😋🍔🍕🍖🍗🍚🍞🍟🍠🍣🍤🍩🍪",
                        "https://res.cloudinary.com/collections-uz/image/upload/v1655572241/cld-sample-4.jpg",
                        foods));
                customFieldRepository.save(new CustomField("Description",
                        FieldDataType.textarea,
                        food));
                customFieldRepository.save(new CustomField("Photo",
                        FieldDataType.file,
                        food));
            }
        }
    }
}
