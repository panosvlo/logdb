package gr.uoa.di.cs.logdb.repository;

import gr.uoa.di.cs.logdb.model.LogDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogDetailRepository extends JpaRepository<LogDetail, Long> {
    // Find all LogDetails by the Log ID
    List<LogDetail> findAllByLogId(Long logId);
}
