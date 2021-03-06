package life.yang.community.studycommunity.controller;

import life.yang.community.studycommunity.dto.AccessTokenDto;
import life.yang.community.studycommunity.dto.GithubUser;
import life.yang.community.studycommunity.model.User;
import life.yang.community.studycommunity.provider.GithubProvider;
import life.yang.community.studycommunity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class AuthorizeController {

    private final GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    private final UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response) {
        final AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setClient_id(clientId);
        accessTokenDto.setClient_secret(clientSecret);
        accessTokenDto.setRedirect_uri(redirectUri);
        accessTokenDto.setCode(code);
        accessTokenDto.setState(state);
        final String accessToken = githubProvider.getAccessToken(accessTokenDto);
        final GithubUser githubUser = githubProvider.getUser(accessToken);
        if (githubUser != null) {
            final User user = new User();
            //如果登陆成功，将用户信息保存在数据库中
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setName(githubUser.getName());
            user.setBio(githubUser.getBio());
            user.setAvatarUrl(githubUser.getAvatarUrl());
            final String token = UUID.randomUUID().toString();
            user.setToken(token);
            userService.createOrUpdate(user);
                //并将token加入session
            response.addCookie(new Cookie("token",token));
        }
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        request.getSession().removeAttribute("user");
        final Cookie cookie = new Cookie("token", null);
        response.addCookie(cookie);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
