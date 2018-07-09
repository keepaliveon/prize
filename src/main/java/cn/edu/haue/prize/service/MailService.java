package cn.edu.haue.prize.service;

import cn.edu.haue.prize.constant.Result;
import cn.edu.haue.prize.constant.ResultCode;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

/**
 * Created by 杨晋升 on 2018-07-04.
 */
@Service
@Transactional
public class MailService {

    @Resource
    private JavaMailSender mailSender;

    //发邮件
    public Result sendMail(String email, String subject, String content) throws MessagingException {
        Result result = new Result();
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
        message.setFrom("vit586@163.com");
        message.setSubject(subject);
        message.setTo(email);
        message.setText(content);
        mailSender.send(message.getMimeMessage());
        result.setResultCode(ResultCode.RESULT_CODE_SUCCESS);
        return result;
    }
}
