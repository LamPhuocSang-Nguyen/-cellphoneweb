package com.example.cellphoneweb.repositorise;

import com.example.cellphoneweb.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findById(int user_Id);

    List<User> findByEmail(String email);

}
