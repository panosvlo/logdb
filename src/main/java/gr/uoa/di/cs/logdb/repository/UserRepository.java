package gr.uoa.di.cs.logdb.repository;

import gr.uoa.di.cs.logdb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Find a user by their login name
    User findByLogin(String login);
}
