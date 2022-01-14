package ca.maickel.posts.service;

import ca.maickel.posts.model.Post;

import java.util.List;

public interface PostService {
    List<Post> getPosts(String tags, String sortBy, String direction);
}
