package life.yang.community.studycommunity.controller;

import life.yang.community.studycommunity.dto.PaginationDto;
import life.yang.community.studycommunity.mapper.UserMapper;
import life.yang.community.studycommunity.model.User;
import life.yang.community.studycommunity.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final QuestionService questionService;

    @GetMapping("/")
    public String index(@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                        @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
                        Model model) {
        PaginationDto pagination = questionService.getQuestionList(page, size);
        model.addAttribute("pagination", pagination);
        return "index";
    }
}
