package ca.maickel.posts.model.helper;

import ca.maickel.posts.model.Post;

import java.util.Arrays;
import java.util.Comparator;

public class PostComparator implements Comparator<Post> {

    private final String comparatorField;
    private final String direction;

    private static final String[] validFields = {"id", "reads", "likes", "popularity"};
    private static final String[] validDirection = {"desc", "asc"};

    public PostComparator(String comparatorField, String direction) {
        if (comparatorField != null
                && !comparatorField.isEmpty()
                && Arrays.stream(validFields).noneMatch(comparatorField::equals)) {
            throw new IllegalArgumentException("sortBy parameter is invalid");
        };
        if (direction != null
                && !direction.isEmpty()
                && Arrays.stream(validDirection).noneMatch(direction::equals)) {
            throw new IllegalArgumentException("direction parameter is invalid");
        };
        this.comparatorField = comparatorField;
        this.direction = direction;
    }

    @Override
    public int compare(Post post1, Post post2) {
        int result;
        if(comparatorField == null) {
            return 0;
        }
        switch (comparatorField) {
            case "id":
                result = post1.getId().compareTo(post2.getId());
                break;
            case "reads":
                result = post1.getReads().compareTo(post2.getReads());
                break;
            case "likes":
                result = post1.getLikes().compareTo(post2.getLikes());
                break;
            case "popularity":
                result = post1.getPopularity().compareTo(post2.getPopularity());
                break;
            default:
                result = 0;
        }
        if("desc".equals(direction)) {
            result *= -1;
        }

        return result;
    }
}
