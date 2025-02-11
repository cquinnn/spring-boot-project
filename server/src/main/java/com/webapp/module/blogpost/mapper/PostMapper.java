package com.webapp.module.blogpost.mapper;

import com.webapp.module.blogpost.dto.PostResponse;
import com.webapp.model.Post;
import org.mapstruct.Mapper;

import com.webapp.module.blogpost.dto.UploadPostRequest;

@Mapper(componentModel = "spring")
public interface PostMapper {
    Post toPost(UploadPostRequest request);
    PostResponse toPostResponse(Post post);
}
