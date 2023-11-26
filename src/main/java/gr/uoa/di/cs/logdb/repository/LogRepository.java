package gr.uoa.di.cs.logdb.repository;

import gr.uoa.di.cs.logdb.dto.*;
import gr.uoa.di.cs.logdb.model.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
            @Param("endDate") Date endDate);@Query("SELECT new gr.uoa.di.cs.logdb.dto.MostCommonLogDTO(l.sourceIp, lt.typeName, COUNT(l)) " +
            "FROM Log l JOIN LogType lt ON l.logTypeId = lt.id " +
            "WHERE DATE(l.timestamp) = :specificDate " +
            "GROUP BY l.sourceIp, lt.typeName " +
            "ORDER BY l.sourceIp, COUNT(l) DESC")
    List<MostCommonLogDTO> findMostCommonLogByDate(Date specificDate);

    @Query(value = "SELECT ld.value, DATE(l.timestamp) as logDate, COUNT(l) as totalActions " +
            "FROM logs l JOIN log_details ld ON l.id = ld.log_id " +
            "WHERE ld.key = 'block_id' AND l.timestamp BETWEEN :startDate AND :endDate " +
            "GROUP BY ld.value, DATE(l.timestamp) " +
            "ORDER BY COUNT(l) DESC, DATE(l.timestamp)",
            nativeQuery = true)
    Page<Object[]> findTopBlockActionsBetweenDates(Date startDate, Date endDate, Pageable pageable);

    @Query(value = "SELECT ld.value AS resourceName, COUNT(ld.value) AS resourceCount " +
            "FROM log_details ld " +
            "WHERE ld.key = 'resource' " +
            "GROUP BY ld.value " +
            "ORDER BY COUNT(ld.value) DESC " +
            "LIMIT 1 OFFSET 1",
            nativeQuery = true)
    List<Object[]> findSecondMostCommonResourceRaw();
}