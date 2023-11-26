package gr.uoa.di.cs.logdb.repository;

import gr.uoa.di.cs.logdb.dto.LogCountDTO;
import gr.uoa.di.cs.logdb.dto.LogCountPerDayDTO;
import gr.uoa.di.cs.logdb.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.Date;
import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
    @Query("SELECT new gr.uoa.di.cs.logdb.dto.LogCountDTO(l.logTypeId, count(l)) " +
            "FROM Log l WHERE l.timestamp BETWEEN :startDate AND :endDate " +
            "GROUP BY l.logTypeId ORDER BY count(l) DESC")
    List<LogCountDTO> findLogCountByTypeAndDateRange(Date startDate, Date endDate);

    @Query(value = "SELECT CAST(l.timestamp AS date) as logDate, COUNT(*) as totalLogs " +
            "FROM logs l " +
            "JOIN log_details ld ON l.id = ld.log_id " +
            "WHERE ld.key = 'action' AND ld.value = :action " +
            "AND l.timestamp BETWEEN :startDate AND :endDate " +
            "GROUP BY CAST(l.timestamp AS date) " +
            "ORDER BY CAST(l.timestamp AS date)", nativeQuery = true)
    List<Object[]> findTotalLogsPerDayForActionType(
            @Param("action") String action,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);
}