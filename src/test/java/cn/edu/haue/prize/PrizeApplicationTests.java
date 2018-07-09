package cn.edu.haue.prize;

import cn.edu.haue.prize.entity.User;
import cn.edu.haue.prize.repository.UserRepository;
import cn.edu.haue.prize.service.MailService;
import cn.edu.haue.prize.utils.PasswordUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.mail.MessagingException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PrizeApplicationTests {

    @Resource
    private UserRepository userRepository;

    @Resource
    private MailService mailService;

    @Test
    public void contextLoads() {
        User user1 = new User("zhangsan@163.com", "张三", PasswordUtils.getPasswordEncrypter().encode("123"));
        user1.setRole("ROLE_ADMIN");
//        User user2 = new User("lisi@163.com", "李四", PasswordUtils.getPasswordEncrypter().encode("123"));
//        user2.setRole("ROLE_DIRECTOR");
//        User user3 = new User("wangwu@163.com", "王五", PasswordUtils.getPasswordEncrypter().encode("123"));
//        user3.setRole("ROLE_LEADER");
//        User user4 = new User("zhaoliu@163.com", "赵六", PasswordUtils.getPasswordEncrypter().encode("123"));
//        user4.setRole("ROLE_WORKER");
//        userRepository.save(user1);
//        userRepository.save(user2);
//        userRepository.save(user3);
        userRepository.save(user1);
    }

    @Test
    public void test1() throws MessagingException {
        mailService.sendMail("vit125@163.com", "Hello", "Hello World");
    }
}
