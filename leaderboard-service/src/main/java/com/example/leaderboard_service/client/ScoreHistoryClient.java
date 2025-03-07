package com.example.leaderboard_service.client;

import com.example.leaderboard_service.dto.ScoreSubmissionRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "score-history-service")
public interface ScoreHistoryClient {
    @PostMapping("/score-history/record")
    void recordScore(ScoreSubmissionRequest request);
}
