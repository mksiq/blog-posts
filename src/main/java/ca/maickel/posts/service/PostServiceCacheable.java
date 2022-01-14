package ca.maickel.posts.service;

import ca.maickel.posts.model.Post;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public interface PostServiceCacheable {
        List<Post> getPost(String health);
}
