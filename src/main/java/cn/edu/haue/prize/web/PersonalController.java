package cn.edu.haue.prize.web;

import cn.edu.haue.prize.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 杨晋升 on 2018-07-04.
 */
@Controller
public class PersonalController {

    @Resource
    private UserService userService;

    @RequestMapping("/personal")
    public ModelAndView personal() {
        ModelAndView modelAndView = new ModelAndView("personal");
        List<String> roles = new ArrayList<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        for (GrantedAuthority next : authentication.getAuthorities()) {
            roles.add(next.getAuthority());
        }
        modelAndView.addObject("roles", roles);
        modelAndView.addObject("user", userService.getUser(authentication.getName()).getResultObj());
        return modelAndView;
    }
}
