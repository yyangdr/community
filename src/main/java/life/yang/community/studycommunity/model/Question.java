package life.yang.community.studycommunity.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Question {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
    private Long creator;
    private Long commentCount;
    private Long viewCount;
    private Long likeCount;
    private String tag;
}
