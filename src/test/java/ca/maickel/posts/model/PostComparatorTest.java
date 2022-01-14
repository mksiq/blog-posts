package ca.maickel.posts.model;

import ca.maickel.posts.model.helper.PostComparator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

public class PostComparatorTest {

    @Test
    public void testConstructorThrowsIllegalArgument() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new PostComparator("abc", "asc"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new PostComparator("id", "abc"));
    }

    @Test
    public void testCompare() {
        Post p1 = Post.builder()
                .id(2)
                .author("Maickel Siqueira")
                .authorId(1)
                .likes(new BigInteger(String.valueOf(32)))
                .reads(new BigInteger(String.valueOf(64)))
                .popularity(1.0)
                .build();

        Post p2 = Post.builder()
                .id(1)
                .author("Maickel Siqueira")
                .authorId(1)
                .likes(new BigInteger(String.valueOf(55)))
                .reads(new BigInteger(String.valueOf(63)))
                .popularity(2.0)
                .build();

        PostComparator comparatorIdAsc  = new PostComparator("id", "asc");
        Assertions.assertEquals(1, comparatorIdAsc.compare(p1, p2));
        PostComparator comparatorIdDesc  = new PostComparator("id", "desc");
        Assertions.assertEquals(-1, comparatorIdDesc.compare(p1, p2));
        PostComparator comparatorReads  = new PostComparator("reads", null);
        Assertions.assertEquals(1, comparatorReads.compare(p1, p2));
        PostComparator comparatorLikes  = new PostComparator("likes", null);
        Assertions.assertEquals(-1, comparatorLikes.compare(p1, p2));
        PostComparator comparatorPopularity  = new PostComparator("popularity", null);
        Assertions.assertEquals(-1, comparatorPopularity.compare(p1, p2)); ;
        PostComparator comparatorEmpty  = new PostComparator(null, null);
        Assertions.assertEquals(0, comparatorEmpty.compare(p1, p2)); ;
    }
}
