package com.example.history_service.controller;

import com.example.history_service.dto.TopPlayerDTO;
import com.example.history_service.model.ScoreRecord;
import com.example.history_service.service.ScoreHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/score-history")
public class ScoreHistoryController {

    @Autowired
    private ScoreHistoryService scoreHistoryService;

    @PostMapping("/record")
    public ResponseEntity<ScoreRecord> recordScore(@RequestBody ScoreRecord record) {
        // Ensure the record has a timestamp; if not, set current time.
        if (record.getTimestamp() == null) {
            record.setTimestamp(LocalDateTime.now());
        }
        ScoreRecord savedRecord = scoreHistoryService.recordScore(record);
        return ResponseEntity.ok(savedRecord);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ScoreRecord>> getUserScoreHistory(@PathVariable("userId") String userId) {
        List<ScoreRecord> records = scoreHistoryService.getUserScoreHistory(userId);
        return ResponseEntity.ok(records);
    }

    @GetMapping("/top-players")
    public ResponseEntity<List<TopPlayerDTO>> getTopPlayers(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<TopPlayerDTO> topPlayers = scoreHistoryService.getTopPlayers(startDate, endDate);
        return ResponseEntity.ok(topPlayers);
    }
}
