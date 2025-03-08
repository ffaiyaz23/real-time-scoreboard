package com.example.history_service.service;

import com.example.history_service.dto.TopPlayerDTO;
import com.example.history_service.model.ScoreRecord;
import com.example.history_service.repo.ScoreRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScoreHistoryService {

    @Autowired
    private ScoreRecordRepository scoreRecordRepository;

    public ScoreRecord recordScore(ScoreRecord record) {
        return scoreRecordRepository.save(record);
    }

    public List<ScoreRecord> getUserScoreHistory(String userId) {
        return scoreRecordRepository.findByUserId(userId);
    }

    public List<TopPlayerDTO> getTopPlayers(LocalDateTime startDate, LocalDateTime endDate) {
        return scoreRecordRepository.findTopPlayers(startDate, endDate);
    }
}
