package cn.edu.haue.prize.web;

import cn.edu.haue.prize.constant.ResultCode;
import cn.edu.haue.prize.service.ApplicationService;
import cn.edu.haue.prize.service.UserService;
import cn.edu.haue.prize.utils.UUIDUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 杨晋升 on 2018-07-03.
 */
@Controller
public class ApplyController {

    @Resource
    private ApplicationService applicationService;

    @Resource
    private UserService userService;

    @RequestMapping("apply")
    @PreAuthorize("hasRole('ROLE_WORKER')")
    public ModelAndView application() {
        ModelAndView modelAndView = new ModelAndView("apply");
        return modelAndView;
    }

    @RequestMapping("submit_application")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_WORKER')")
    public String submit_apply(String name, String type, MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String serialNum = UUIDUtils.SerialNum();
            File material = new File("D:/material/" + serialNum + "/" + fileName);
//            File material = new File("/root/material/" + serialNum + "/" + fileName);
            if (!material.getParentFile().exists()) {
                material.getParentFile().mkdirs();
            }
            file.transferTo(material);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = userService.getUsernameByEmail(authentication.getName()).getResultObj();
            if (applicationService.SubmitApplication(serialNum, name, type, file.getOriginalFilename(), username).getResultCode().equals(ResultCode.RESULT_CODE_SUCCESS)) {
                return "success";
            } else {
                return "error";
            }
        } else {
            return "error";
        }
    }

    @RequestMapping("publicity")
    public ModelAndView publicity() {
        ModelAndView modelAndView = new ModelAndView("publicity");
        modelAndView.addObject("applications", applicationService.getApplicationList("CONFIRMED").getResultObj());
        List<String> roles = new ArrayList<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        for (GrantedAuthority next : authentication.getAuthorities()) {
            roles.add(next.getAuthority());
        }
        modelAndView.addObject("roles", roles);
        return modelAndView;
    }

    @RequestMapping("progress")
    @PreAuthorize("hasRole('ROLE_WORKER')")
    public ModelAndView progress() {
        ModelAndView modelAndView = new ModelAndView("progress");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        modelAndView.addObject("applications", applicationService.getWaitingApplicationList(authentication.getName()).getResultObj());
        return modelAndView;
    }

    @RequestMapping("doApplicationRemove")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_WORKER')")
    public String doApplicationRemove(String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (applicationService.deleteApplication(id, authentication.getName()).getResultCode().equals(ResultCode.RESULT_CODE_SUCCESS)) {
            return "success";
        } else {
            return "fail";
        }
    }

    @RequestMapping("doApplicationUpdate")
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_WORKER')")
    public String doApplicationUpdate(String id, MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String serialNum = applicationService.getApplicationByID(id).getResultObj().getId();
            File material = new File("D:/material/" + serialNum + "/" + fileName);
//            File material = new File("/root/material/" + serialNum + "/" + fileName);
            if (!material.getParentFile().exists()) {
                material.getParentFile().mkdirs();
            }
            file.transferTo(material);
            if (applicationService.updateApplication(id, fileName).getResultCode().equals(ResultCode.RESULT_CODE_SUCCESS)) {
                return "success";
            } else {
                return "fail";
            }
        }else {
            return "fail";
        }
    }

    @RequestMapping("download")
    @PreAuthorize("hasAnyRole('ROLE_WORKER','ROLE_DIRECTOR','ROLE_LEADER')")
    public ResponseEntity<byte[]> download(String id) throws IOException {
        String material = applicationService.getApplicationByID(id).getResultObj().getMaterial();
        File file = new File("D:/material/" + id + "/" + material);
//        File file = new File("/root/material/" + id + "/" + material);
        HttpHeaders headers = new HttpHeaders();
        String fileName = new String(file.getName().getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }
}
