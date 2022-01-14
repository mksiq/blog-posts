package ca.maickel.posts.service.impl;

import ca.maickel.posts.model.Post;
import ca.maickel.posts.service.PostServiceCacheable;
import ca.maickel.posts.web.resources.BlogResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Component
@NoArgsConstructor
@AllArgsConstructor
// Handles caching
public class PostServiceCacheableImpl implements PostServiceCacheable {

    @Value("${hatchways.api.uri}")
    private String RESOURCE_PATH;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    @Cacheable("posts")
    public List<Post> getPost(String tag) {
        String uri = RESOURCE_PATH + "/assessment/blog/posts?tag=" + tag;
        ResponseEntity<BlogResponse> result = restTemplate.getForEntity(uri, BlogResponse.class);
        return result.getBody() != null ? result.getBody().getPosts() : Collections.emptyList();
    }

}
