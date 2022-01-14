package ca.maickel.posts.repository;

import ca.maickel.posts.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post, String> {
}
