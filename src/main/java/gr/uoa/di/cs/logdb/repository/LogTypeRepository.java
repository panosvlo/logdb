package gr.uoa.di.cs.logdb.repository;

import gr.uoa.di.cs.logdb.model.LogType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogTypeRepository extends JpaRepository<LogType, Long> {
    // Find a LogType by its type name
    LogType findByTypeName(String typeName);
}
