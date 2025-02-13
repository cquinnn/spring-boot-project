package com.webapp.module.blogpost.mapper;

import com.webapp.model.Post;
import com.webapp.module.blogpost.dto.GetPostResponse;
import com.webapp.module.blogpost.dto.PostResponse;
import com.webapp.module.blogpost.dto.UpdatePostRequest;
import com.webapp.module.blogpost.dto.UploadPostRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PostMapper {
    Post toPost(UploadPostRequest request);

    @Mapping(source = "author.username", target = "author")
    PostResponse toPostResponse(Post post);


    void updatePost(@MappingTarget Post post, UpdatePostRequest request);

    @Mapping(source = "author.username", target = "author")
    GetPostResponse toGetPostResponse(Post post);
}
