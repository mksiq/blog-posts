package ca.maickel.posts.service;

import ca.maickel.posts.model.Post;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public interface PostService {
    List<Post> getPosts(String tags, String sortBy, String direction);

    List<Post> getPost(String health, RestTemplate restTemplate);
}
