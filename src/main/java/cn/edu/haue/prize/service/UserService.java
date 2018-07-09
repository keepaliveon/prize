package cn.edu.haue.prize.service;

import cn.edu.haue.prize.constant.Result;
import cn.edu.haue.prize.constant.ResultCode;
import cn.edu.haue.prize.entity.User;
import cn.edu.haue.prize.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by 杨晋升 on 2018-07-02.
 */
@Service
@Transactional
public class UserService implements UserDetailsService {

    @Resource
    private UserRepository userRepository;

    //Spring Security加载用户
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if (user != null) {
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            String role = user.getRole();
            if (role != null) {
                authorities.add(new SimpleGrantedAuthority(role));
            }
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
        } else {
            throw new UsernameNotFoundException("用户名不存在");
        }
    }

    //通过邮箱找用户名
    public Result<String> getUsernameByEmail(String email) {
        Result<String> result = new Result<>();
        User user = userRepository.findUserByEmail(email);
        if (user != null) {
            result.setResultObj(user.getUsername());
            result.setResultCode(ResultCode.RESULT_CODE_SUCCESS);
        }
        return result;
    }

    //获取所有用户
    public Result<List<User>> getAllUser() {
        Result<List<User>> result = new Result<>();
        result.setResultObj(userRepository.findAll());
        result.setResultCode(ResultCode.RESULT_CODE_SUCCESS);
        return result;
    }

    //添加用户
    public Result addUser(User user) {
        Result result = new Result();
        User save = userRepository.save(user);
        result.setResultCode(ResultCode.RESULT_CODE_SUCCESS);
        return result;
    }

    //根据邮箱获取用户
    public Result<User> getUser(String s) {
        Result<User> result = new Result<>();
        result.setResultObj(userRepository.findUserByEmail(s));
        result.setResultCode(ResultCode.RESULT_CODE_SUCCESS);
        return result;
    }
}