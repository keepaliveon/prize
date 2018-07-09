package cn.edu.haue.prize.repository;

import cn.edu.haue.prize.entity.Application;
import cn.edu.haue.prize.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by 杨晋升 on 2018-07-03.
 */
public interface ApplicationRepository extends JpaRepository<Application, String> {

    List<Application> findApplicationsByStatus(String status);

    Application findApplicationById(String id);

    List<Application> findApplicationsByUser(User user);
}
