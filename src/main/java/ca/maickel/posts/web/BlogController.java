package ca.maickel.posts.web;

import ca.maickel.posts.model.Post;
import ca.maickel.posts.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "Endpoint for Posts")
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class BlogController {

    @Autowired
    PostService postService;

    @ApiOperation(httpMethod = "GET", value = "Get posts filtered by tag", response = Post.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid request parameters"),
            @ApiResponse(code = 404, message = "Posts not found"),
            @ApiResponse(code = 500, message = "The posts could not be fetched")
    })
    @GetMapping("/posts")
    public List<Post> getPosts(@RequestParam String tags, @RequestParam(required = false) String sortBy, @RequestParam(required = false) String direction) {
        return postService.getPosts(tags, sortBy, direction);
    }

}
