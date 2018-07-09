package cn.edu.haue.prize.web;

import cn.edu.haue.prize.constant.ResultCode;
import cn.edu.haue.prize.service.ApplicationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * Created by 杨晋升 on 2018-07-04.
 */
@Controller
public class ConfirmController {

    @Resource
    private ApplicationService applicationService;

    @RequestMapping("/confirm")
    @PreAuthorize("hasRole('ROLE_LEADER')")
    public ModelAndView confirm() {
        ModelAndView modelAndView = new ModelAndView("confirm");
        modelAndView.addObject("applications", applicationService.getApplicationList("PASSED").getResultObj());
        return modelAndView;
    }

    @RequestMapping("/doConfirm")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_LEADER')")
    public String doConfirm(String id, String status) {
        if (applicationService.confirmApplication(id, status).getResultCode().equals(ResultCode.RESULT_CODE_SUCCESS)) {
            return "success";
        } else {
            return "fail";
        }
    }
}
