package com.gasimov.register_login.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.gasimov.register_login.model.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User findByEmail(String email);}
