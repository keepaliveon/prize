package cn.edu.haue.prize.repository;

import cn.edu.haue.prize.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by 杨晋升 on 2018-07-02.
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserByUsername(String username);

    User findUserByEmail(String email);
}
