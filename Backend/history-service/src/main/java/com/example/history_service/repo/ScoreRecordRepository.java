package com.example.history_service.repo;

import com.example.history_service.dto.TopPlayerDTO;
import com.example.history_service.model.ScoreRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ScoreRecordRepository extends JpaRepository<ScoreRecord, Long> {

    List<ScoreRecord> findByUserId(String userId);

    @Query("SELECT new com.example.history_service.dto.TopPlayerDTO(sr.userId, SUM(sr.score)) " +
            "FROM ScoreRecord sr " +
            "WHERE sr.timestamp BETWEEN :startDate AND :endDate " +
            "GROUP BY sr.userId " +
            "ORDER BY SUM(sr.score) DESC")
    List<TopPlayerDTO> findTopPlayers(@Param("startDate") LocalDateTime startDate,
                                      @Param("endDate") LocalDateTime endDate);
}
