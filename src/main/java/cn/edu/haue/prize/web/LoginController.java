package cn.edu.haue.prize.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;


/**
 * Created by 杨晋升 on 2018-07-02.
 */
@Controller
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/")
    public String root(HttpServletRequest request) {
        logger.info(request.getRemoteHost() + "访问根路径");
        return "redirect:login";
    }

    @GetMapping("login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @RequestMapping("success")
    public String success() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        logger.info(authentication.getName() + "成功登陆");
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String role = null;
        for (GrantedAuthority authority : authorities) {
            role = authority.getAuthority();
        }
        if (role != null) {
            switch (role) {
                case "ROLE_WORKER":
                    return "redirect:/apply";
                case "ROLE_DIRECTOR":
                    return "redirect:/verify";
                case "ROLE_LEADER":
                    return "redirect:/confirm";
                case "ROLE_ADMIN":
                    return "redirect:/admin";
            }
        }
        return "error";
    }
}
