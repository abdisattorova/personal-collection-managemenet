package com.itransition.itransitioncoursework.projection;


import java.util.UUID;

public interface CommentProjection {

    UUID getId();

    String getContent();

    String getTime();

    String getDate();

    UUID getAuthorId();

    String getAuthorName();

}
