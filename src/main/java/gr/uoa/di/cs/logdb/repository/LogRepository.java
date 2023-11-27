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

    @Query(value = "SELECT l.id, l.log_type_id, l.timestamp, l.source_ip, l.destination_ip, STRING_AGG(ld.key || ': ' || ld.value, '; ') " +
            "FROM logs l JOIN log_details ld ON l.id = ld.log_id " +
            "JOIN log_types lt ON l.log_type_id = lt.id " +
            "WHERE lt.type_name = 'access_log' " +
            "AND EXISTS (SELECT 1 FROM log_details ld2 WHERE ld2.log_id = l.id AND ld2.key = 'size' AND CAST(ld2.value AS INTEGER) < :size) " +
            "GROUP BY l.id, l.log_type_id, l.timestamp, l.source_ip, l.destination_ip",
            nativeQuery = true)
    List<Object[]> findAccessLogsWithSizeLessThanRaw(@Param("size") int size);
    @Query(value = "SELECT l.id, l.log_type_id, l.timestamp, l.source_ip, l.destination_ip, STRING_AGG(ld.key || ': ' || ld.value, '; ') " +
            "FROM logs l JOIN log_details ld ON l.id = ld.log_id " +
            "JOIN log_types lt ON l.log_type_id = lt.id " +
            "WHERE lt.type_name = 'access_log' AND ld.value LIKE '%Firefox%' " +
            "GROUP BY l.id, l.log_type_id, l.timestamp, l.source_ip, l.destination_ip",
            nativeQuery = true)
    List<Object[]> findAccessLogsByFirefoxRaw();
    @Query("SELECT new gr.uoa.di.cs.logdb.dto.MethodUsageDTO(l.sourceIp, COUNT(*)) " +
            "FROM Log l JOIN LogDetail ld ON l.id = ld.logId " +
            "JOIN LogType lt ON l.logTypeId = lt.id " +
            "WHERE lt.typeName = 'access_log' AND ld.key = 'method' " +
            "AND ld.value = :httpMethod " +
            "AND l.timestamp BETWEEN :startDate AND :endDate " +
            "GROUP BY l.sourceIp " +
            "ORDER BY COUNT(*) DESC")
    List<MethodUsageDTO> findMethodUsage(
            @Param("httpMethod") String httpMethod,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);



    @Query(value = "SELECT l1.source_ip, COUNT(DISTINCT l1.id) as request_count " +
            "FROM logs l1 " +
            "JOIN log_details ld1 ON l1.id = ld1.log_id " +
            "JOIN log_types lt1 ON l1.log_type_id = lt1.id " +
            "WHERE lt1.type_name = 'access_log' " +
            "AND ld1.key = 'method' " +
            "AND ld1.value = :method1 " +
            "AND l1.timestamp BETWEEN :startDate AND :endDate " +
            "AND EXISTS ( " +
            "    SELECT 1 FROM logs l2 " +
            "    JOIN log_details ld2 ON l2.id = ld2.log_id " +
            "    WHERE l1.source_ip = l2.source_ip " +
            "    AND ld2.key = 'method' " +
            "    AND ld2.value = :method2 " +
            "    AND l2.timestamp BETWEEN :startDate AND :endDate " +
            ") " +
            "GROUP BY l1.source_ip " +
            "ORDER BY request_count DESC", nativeQuery = true)
    List<Object[]> findIPsWithTwoMethods(
            @Param("method1") String method1,
            @Param("method2") String method2,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate);

    @Query(value = "SELECT l.source_ip, COUNT(DISTINCT ld.value) as distinct_methods_count " +
            "FROM logs l JOIN log_details ld ON l.id = ld.log_id " +
            "JOIN log_types lt ON l.log_type_id = lt.id " +
            "WHERE lt.type_name = 'access_log' " +
            "AND ld.key = 'method' " +
            "AND l.timestamp BETWEEN :startDate AND :endDate " +
            "GROUP BY l.source_ip " +
            "HAVING COUNT(DISTINCT ld.value) >= :minMethods " +
            "ORDER BY distinct_methods_count DESC", nativeQuery = true)
    List<Object[]> findIPsWithDistinctMethods(
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate,
            @Param("minMethods") int minMethods);
}