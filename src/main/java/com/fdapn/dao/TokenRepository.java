package com.fdapn.dao;

import com.fdapn.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

  @Query(value = """
    select t from Token t
    inner join t.user u
    where u.id = :id and (t.expired = false or t.revoked = false)
    """)
  List<Token> findAllValidTokenByUser(Integer id);

  @Query("SELECT t FROM Token t WHERE t.token = :token")
  Optional<Token> findByToken(@Param("token") String token);
}
