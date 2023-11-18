package gr.uoa.di.cs.logdb.repository;

import gr.uoa.di.cs.logdb.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
    // Custom query methods can be added here if necessary
}
