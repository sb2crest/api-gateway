package com.fdapn.dao;

import java.util.Optional;

import com.fdapn.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {
  @Query("SELECT u FROM User u WHERE u.email = :email")
  Optional<User> findByEmail(@Param("email") String email);
  @Query("SELECT u FROM User u WHERE u.userId = :userId")
  Optional<User> findByUserId(@Param("userId") String userId);
  @Query("SELECT u FROM User u WHERE u.email = :email OR u.userId = :userId")
  Optional<User> findByEmailOrUserId(@Param("email") String email, @Param("userId") String userId);

}
