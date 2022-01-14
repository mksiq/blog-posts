package ca.maickel.posts.service.impl;

import ca.maickel.posts.exception.PostNotFoundException;
import ca.maickel.posts.model.Post;
import ca.maickel.posts.service.PostService;
import ca.maickel.posts.service.PostServiceCacheable;
import ca.maickel.posts.web.resources.BlogResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PostServiceImplTest {

    PostService postService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private PostServiceCacheable postServiceCacheableImpl;

    private final String RESOURCE_PATH = "${hatchways.api.uri}";

    @BeforeEach
    public void setup() {
        postServiceCacheableImpl = new PostServiceCacheableImpl(RESOURCE_PATH, restTemplate);
        postService = new PostServiceImpl(postServiceCacheableImpl);
    }

    @Test
    public void testGetPost() {
        String healthUri = RESOURCE_PATH + "/assessment/blog/posts?tag=health";

        List<Post> posts = Collections.singletonList(Post.builder()
                .id(1)
                .author("Maickel Siqueira")
                .authorId(1)
                .likes(new BigInteger(String.valueOf(42)))
                .reads(new BigInteger(String.valueOf(42)))
                .popularity(1.0)
                .build());
        BlogResponse blog1 = BlogResponse.builder().posts(posts).build();
        when(restTemplate.getForEntity(healthUri, BlogResponse.class))
                .thenReturn(new ResponseEntity<>(blog1, HttpStatus.OK));

        List<Post> result1 = postServiceCacheableImpl.getPost("health");

        Assertions.assertEquals(result1, posts);

        BlogResponse blog2 = BlogResponse.builder().posts(Collections.emptyList()).build();

        when(restTemplate.getForEntity(healthUri, BlogResponse.class))
                .thenReturn(new ResponseEntity<>(blog2, HttpStatus.OK));

        List<Post> result2 = postServiceCacheableImpl.getPost("health");

        Assertions.assertTrue(result2.isEmpty());
    }

    @Test
    public void testGetPostsThrowsIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> postService.getPosts(null, null, null));
    }

    @Test
    public void testGetPosts() {
        String uriHealth = RESOURCE_PATH + "/assessment/blog/posts?tag=health";
        String uriTech = RESOURCE_PATH + "/assessment/blog/posts?tag=tech";

        List<Post> postsHealth = Collections.singletonList(Post.builder()
                .id(2)
                .author("Maickel Siqueira")
                .authorId(1)
                .likes(new BigInteger(String.valueOf(42)))
                .reads(new BigInteger(String.valueOf(42)))
                .popularity(1.0)
                .build());

        List<Post> postsTech= Collections.singletonList(Post.builder()
                .id(1)
                .author("Maickel Siqueira")
                .authorId(1)
                .likes(new BigInteger(String.valueOf(24)))
                .reads(new BigInteger(String.valueOf(24)))
                .popularity(2.0)
                .build());

        BlogResponse blog1 = BlogResponse.builder().posts(postsHealth).build();
        when(restTemplate.getForEntity(uriHealth, BlogResponse.class))
                .thenReturn(new ResponseEntity<>(blog1, HttpStatus.OK));

        BlogResponse blog2 = BlogResponse.builder().posts(postsTech).build();
        when(restTemplate.getForEntity(uriTech, BlogResponse.class))
                .thenReturn(new ResponseEntity<>(blog2, HttpStatus.OK));


        List<Post> result1 = postService.getPosts("health;tech", "id", "desc");

        List<Post> expected = Stream.of(postsHealth, postsTech )
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        Assertions.assertEquals(result1, expected);
    }

    @Test
    public void testGetPostsThrowsPostNotFoundException() {
        String uri = RESOURCE_PATH + "/assessment/blog/posts?tag=health";

        List<Post> postsHealth = Collections.emptyList();

        BlogResponse blog = BlogResponse.builder().posts(postsHealth).build();
        when(restTemplate.getForEntity(uri, BlogResponse.class))
                .thenReturn(new ResponseEntity<>(blog, HttpStatus.OK));

        Assertions.assertThrows(PostNotFoundException.class,
                () ->  postService.getPosts("health", "id", "desc"));
    }
}
