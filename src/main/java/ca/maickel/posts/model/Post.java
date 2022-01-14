package ca.maickel.posts.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.math.BigInteger;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class Post {
    @Id
    Integer id;
    String author;
    Integer authorId;
    BigInteger likes;
    Double popularity;
    BigInteger reads;
    Set<String> tags;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        return id.equals(post.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
