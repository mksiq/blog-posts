package ca.maickel.posts.web;

import ca.maickel.posts.model.Post;
import ca.maickel.posts.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Endpoint for Posts")
@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class BlogController {

    @Autowired
    PostService postService;

    @Operation(summary = "GET Get posts filtered by tag",
            responses = {
            @ApiResponse(responseCode = "200", description = "List of posts"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "404", description = "Posts not found"),
            @ApiResponse(responseCode = "500", description = "The posts could not be fetched")
    })
    @GetMapping("/posts")
    public List<Post> getPosts(@RequestParam String tags, @RequestParam(required = false) String sortBy, @RequestParam(required = false) String direction) {
        return postService.getPosts(tags, sortBy, direction);
    }

    @Operation(summary = "GET Get posts filtered by tag",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success"),
                    @ApiResponse(responseCode = "500", description = "Server error")
            })
    @GetMapping("/ping")
    public JSONObject getStatus() {
        return new JSONObject("{success: true}");
    }


}
