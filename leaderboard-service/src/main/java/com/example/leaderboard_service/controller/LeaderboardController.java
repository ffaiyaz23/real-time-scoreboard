package com.example.leaderboard_service.controller;

import com.example.leaderboard_service.dto.ScoreSubmissionRequest;
import com.example.leaderboard_service.dto.LeaderboardResponse;
import com.example.leaderboard_service.dto.RankResponse;
import com.example.leaderboard_service.service.LeaderboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/leaderboards")
public class LeaderboardController {

    @Autowired
    private LeaderboardService leaderboardService;

    // Endpoint for score submission
    @PostMapping("/{gameId}/score")
    public String submitScore(@PathVariable("gameId") String gameId, @RequestBody ScoreSubmissionRequest request) {
        leaderboardService.submitScore(gameId, request.getUserId(), request.getScore());
        return "Score submitted successfully";
    }

    // Endpoint to retrieve top N players
    @GetMapping("/{gameId}")
    public LeaderboardResponse getLeaderboard(@PathVariable("gameId") String gameId, @RequestParam(defaultValue = "10") long limit) {
        Set<TypedTuple<String>> topPlayers = leaderboardService.getTopPlayers(gameId, limit);
        LeaderboardResponse response = new LeaderboardResponse();
        response.setLeaderboard(topPlayers);
        return response;
    }

    // Endpoint to get a specific user's rank
    @GetMapping("/{gameId}/rank/{userId}")
    public RankResponse getUserRank(@PathVariable("gameId") String gameId, @PathVariable("userId") String userId) {
        Long rank = leaderboardService.getUserRank(gameId, userId);
        RankResponse response = new RankResponse();
        response.setRank(rank);
        return response;
    }
}
