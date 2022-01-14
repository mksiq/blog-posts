package ca.maickel.posts.web;

import ca.maickel.posts.model.Post;
import ca.maickel.posts.service.PostService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BlogControllerTest {

    @Mock
    PostService postService;

    BlogController controller;

    @BeforeEach
    public void setup () {
        controller = new BlogController(postService);
    }

    @Test
    public void getPosts() {
        List<Post> posts = Collections.singletonList(Post.builder()
                .id(1)
                .author("Maickel Siqueira")
                .authorId(1)
                .likes(new BigInteger(String.valueOf(42)))
                .reads(new BigInteger(String.valueOf(42)))
                .popularity(1.0)
                .build());

        when(postService.getPosts("health", null, null)).thenReturn(posts);
        List<Post> result = controller.getPosts("health", null, null);

        Assertions.assertEquals(posts, result);
    }

}
