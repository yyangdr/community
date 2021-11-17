package life.yang.community.studycommunity.service;

import life.yang.community.studycommunity.dto.PaginationDto;
import life.yang.community.studycommunity.dto.QuestionDto;
import life.yang.community.studycommunity.mapper.QuestionMapper;
import life.yang.community.studycommunity.mapper.UserMapper;
import life.yang.community.studycommunity.model.Question;
import life.yang.community.studycommunity.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionMapper questionMapper;
    private final UserMapper userMapper;

    public PaginationDto getQuestionList(Integer page, Integer size) {
        final PaginationDto paginationDto = new PaginationDto();
        Integer totalCount = questionMapper.count();
        paginationDto.setPagination(page, size, totalCount);
        if(page < 1){
            page = 1;
        }
        if(page > paginationDto.getMaxPage()){
            page = paginationDto.getMaxPage();
        }
        Integer offset = (page - 1) * size;
        final List<Question> questions = questionMapper.list(offset, size);
        List<QuestionDto> questionDtoList = new ArrayList<>();

        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            final QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question, questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        paginationDto.setQuestions(questionDtoList);
        return paginationDto;
    }
}