package dev.fayzullokh.repositories;

import dev.fayzullokh.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
//    @Query("select a from User a where upper(a.username) = upper(?1) ")
    User findByUsername(String username);

    boolean existsByUsername(String baseUsername);
}
