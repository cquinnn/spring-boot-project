package com.webapp.blogpost.mapper;

import com.webapp.blogpost.dto.PostResponse;
import com.webapp.blogpost.entity.Post;
import org.mapstruct.Mapper;

import com.webapp.blogpost.dto.UploadPostRequest;

@Mapper(componentModel = "spring")
public interface PostMapper {
    Post toPost(UploadPostRequest request);
    PostResponse toPostResponse(Post post);
}
