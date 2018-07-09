package cn.edu.haue.prize.service;

import cn.edu.haue.prize.constant.Result;
import cn.edu.haue.prize.constant.ResultCode;
import cn.edu.haue.prize.entity.Application;
import cn.edu.haue.prize.entity.User;
import cn.edu.haue.prize.repository.ApplicationRepository;
import cn.edu.haue.prize.repository.UserRepository;
import cn.edu.haue.prize.utils.UUIDUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Created by 杨晋升 on 2018-07-03.
 */
@Service
@Transactional
public class ApplicationService {

    @Resource
    private ApplicationRepository applicationRepository;

    @Resource
    private UserRepository userRepository;

    //提交申请
    public Result SubmitApplication(String serialNum, String name, String type, String material, String username) {
        Result result = new Result();
        User user = userRepository.findUserByUsername(username);
        Application application = new Application(name, type, material);
        application.setId(serialNum);
        application.setUser(user);
        user.getApplications().add(application);
        try {
            applicationRepository.save(application);
            userRepository.save(user);
            result.setResultCode(ResultCode.RESULT_CODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.setResultCode(ResultCode.RESULT_CODE_FAIL);
        }
        return result;
    }

    //根据状态获取申请列表
    public Result<List<Application>> getApplicationList(String status) {
        Result<List<Application>> result = new Result<>();
        List<Application> applicationList = applicationRepository.findApplicationsByStatus(status);
        result.setResultObj(applicationList);
        result.setResultCode(ResultCode.RESULT_CODE_SUCCESS);
        return result;
    }

    //获取自己已申报的项目
    public Result<List<Application>> getWaitingApplicationList(String email) {
        User user = userRepository.findUserByEmail(email);
        Result<List<Application>> result = new Result<>();
        List<Application> applicationList = applicationRepository.findApplicationsByUser(user);
        result.setResultObj(applicationList);
        result.setResultCode(ResultCode.RESULT_CODE_SUCCESS);
        return result;
    }

    //审核申请
    public Result verifyApplication(String application_id, String status, Integer score, String verifyName) {
        Result result = new Result();
        Application application = applicationRepository.findApplicationById(application_id);
        application.setStatus(status);
        application.setGrade(score);
        application.setVerifyName(verifyName);
        try {
            applicationRepository.save(application);
            result.setResultCode(ResultCode.RESULT_CODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.setResultCode(ResultCode.RESULT_CODE_FAIL);
        }
        return result;
    }

    //确认申请
    public Result confirmApplication(String application_id, String status) {
        Result result = new Result();
        Application application = applicationRepository.findApplicationById(application_id);
        try {
            User user = application.getUser();
            float score = user.getScore();
            user.setScore((float) (score + application.getGrade() * 2.5));
            application.setStatus(status);
            applicationRepository.save(application);
            result.setResultCode(ResultCode.RESULT_CODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.setResultCode(ResultCode.RESULT_CODE_FAIL);
        }
        return result;
    }

    //修改申请
    public Result updateApplication(String application_id, String material) {
        Result result = new Result();
        try {
            Application application = applicationRepository.findApplicationById(application_id);
            application.setMaterial(material);
            application.setUpdateTime(new Date());
            application.setStatus("WAITING");
            application.setGrade(null);
            applicationRepository.save(application);
            result.setResultCode(ResultCode.RESULT_CODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.setResultCode(ResultCode.RESULT_CODE_FAIL);
        }
        return result;
    }

    //放弃申请
    public Result deleteApplication(String application_id, String user_id) {
        Result result = new Result();
        try {
            User user = userRepository.findUserByEmail(user_id);
            Application application = applicationRepository.findApplicationById(application_id);
            user.getApplications().remove(application);
            userRepository.save(user);
            applicationRepository.delete(application);
            result.setResultCode(ResultCode.RESULT_CODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.setResultCode(ResultCode.RESULT_CODE_FAIL);
        }
        return result;
    }

    //根据Id获取申请
    public Result<Application> getApplicationByID(String id) {
        Result<Application> result = new Result<>();
        result.setResultObj(applicationRepository.findApplicationById(id));
        result.setResultCode(ResultCode.RESULT_CODE_SUCCESS);
        return result;
    }
}
