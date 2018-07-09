package cn.edu.haue.prize.web;

import cn.edu.haue.prize.entity.User;
import cn.edu.haue.prize.service.UserService;
import cn.edu.haue.prize.utils.PasswordUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * Created by 杨晋升 on 2018-07-04.
 */
@Controller
public class AdminController {

    @Resource
    private UserService userService;

    @RequestMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView admin() {
        ModelAndView modelAndView = new ModelAndView("admin");
        modelAndView.addObject("users", userService.getAllUser().getResultObj());
        return modelAndView;
    }

    @RequestMapping("/add_user")
    public String add_user(User user) {
        String password = user.getPassword();
        user.setPassword(PasswordUtils.getPasswordEncrypter().encode(password));
        userService.addUser(user);
        return "redirect:admin";
    }
}
