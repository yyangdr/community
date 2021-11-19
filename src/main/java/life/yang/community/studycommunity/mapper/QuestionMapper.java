package life.yang.community.studycommunity.mapper;

import life.yang.community.studycommunity.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question (id,title,description,create_at,modified_at,creator,comment_count,view_count,like_count,tag)" +
            "values (#{id},#{title},#{description},#{createAt},#{modifiedAt},#{creator},#{commentCount},#{viewCount},#{likeCount},#{tag})")
    void create(Question question);

    @Select("select * from question limit #{offset},#{size}")
    List<Question> list(@Param(value = "offset") Integer offset,
                        @Param(value = "size") Integer size);

    @Select("select count(*) from question")
    Integer count();

    @Select("select * from question where creator = #{userId} limit #{offset},#{size}")
    List<Question> listByUserId(@Param("userId") Long userId,
                                @Param("offset") Integer offset,
                                @Param("size") Integer size);

    @Select("select count(*) from question where creator = #{userId}")
    Integer countById(@Param("userId") Long userId);

    @Select("select * from question where id = #{id}")
    Question findById(@Param("id") String id);
}
