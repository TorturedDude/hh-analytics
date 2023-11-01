package com.example.restapi.repositories;

import com.example.restapi.models.User;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    @Query("SELECT * FROM Users u INNER JOIN Subscriptions s ON u.id = s.user_id WHERE s.query = :query")
    List<User> findByQuery(@Param("query") String query);
}
