package cn.edu.haue.prize.web;

import cn.edu.haue.prize.constant.ResultCode;
import cn.edu.haue.prize.entity.Application;
import cn.edu.haue.prize.service.ApplicationService;
import cn.edu.haue.prize.service.UserService;
import com.google.gson.Gson;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * Created by 杨晋升 on 2018-07-03.
 */
@Controller
public class VerifyController {

    @Resource
    private ApplicationService applicationService;

    @Resource
    private UserService userService;

    @RequestMapping("verify")
    @PreAuthorize("hasRole('ROLE_DIRECTOR')")
    public ModelAndView verify() {
        ModelAndView modelAndView = new ModelAndView("verify");
        modelAndView.addObject("applications", applicationService.getApplicationList("WAITING").getResultObj());
        return modelAndView;
    }

    @RequestMapping("doVerify")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_DIRECTOR')")
    public String doVerify(String id, String status, String grade) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String verifyName = userService.getUser(authentication.getName()).getResultObj().getUsername();
        if (grade == null) {
            grade = "0";
        }
        if (applicationService.verifyApplication(id, status, Integer.valueOf(grade), verifyName).getResultCode().equals(ResultCode.RESULT_CODE_SUCCESS)) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping("doGetDetails")
    @ResponseBody
    @PreAuthorize("hasAnyRole('ROLE_DIRECTOR','ROLE_LEADER')")
    public String doGetDetails(String applicationId) {
        Gson gson = new Gson();
        Application application = applicationService.getApplicationByID(applicationId).getResultObj();
        application.getUser().setApplications(null);
        return gson.toJson(application);
    }
}
