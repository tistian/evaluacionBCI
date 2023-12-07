package com.api.evaluacion.repository;

import com.api.evaluacion.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    List<User> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("update User p set p.lastLogin=:lastLogin where p.token=:token")
    void updateLastLogin(Date lastLogin, String token);


    void deleteByEmail(String email);


}
