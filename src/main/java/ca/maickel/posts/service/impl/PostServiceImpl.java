package ca.maickel.posts.service.impl;

import ca.maickel.posts.exception.PostNotFoundException;
import ca.maickel.posts.model.Post;
import ca.maickel.posts.model.helper.PostComparator;
import ca.maickel.posts.service.PostService;
import ca.maickel.posts.service.PostServiceCacheable;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    @Autowired
    private PostServiceCacheable postServiceCacheableImpl;

    public PostServiceImpl() {
    }

    @Override
    public List<Post> getPosts(String tags, String sortBy, String direction)  {
        if (tags == null || tags.isEmpty()) {
            throw new IllegalArgumentException("Tags parameter is required");
        }

        PostComparator comparator = new PostComparator(sortBy, direction);

        List<String> tagsList = Arrays.asList(tags.split(";"));

        List<Post> result = tagsList.stream()
                .map(tag ->
                        CompletableFuture.supplyAsync(() ->
                                postServiceCacheableImpl.getPost(tag)))
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(), completableFutureList ->
                                completableFutureList.stream()
                                        .map(CompletableFuture::join)
                ))
                .flatMap(Collection::stream)
                .distinct()
                .sorted(comparator)
                .collect(Collectors.toList());

        if(result.isEmpty()) {
            throw new PostNotFoundException("No posts found for this tag");
        }

        return result;
    }

//    @Cacheable("posts")
//    public List<Post> getPost(String tag, RestTemplate restTemplate) {
//        System.out.println("waiting for 10");
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("done waiting");
//        String uri = RESOURCE_PATH + "/assessment/blog/posts?tag=" + tag;
//        ResponseEntity<BlogResponse> result = restTemplate.getForEntity(uri, BlogResponse.class);
//        return result.getBody() != null ? result.getBody().getPosts() : Collections.emptyList();
//    }

}
