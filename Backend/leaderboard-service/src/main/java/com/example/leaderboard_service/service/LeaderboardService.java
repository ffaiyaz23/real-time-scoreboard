package com.example.leaderboard_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class LeaderboardService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // Helper to generate Redis key for a given game
    private String getLeaderboardKey(String gameId) {
        return "leaderboard:" + gameId;
    }

    // Submit or update a score for a user
    public void submitScore(String gameId, String userId, double score) {
        String key = getLeaderboardKey(gameId);
        Double currentScore = redisTemplate.opsForZSet().score(key, userId);
        if (currentScore == null || score > currentScore) {
            redisTemplate.opsForZSet().add(key, userId, score);
        }
    }

    // Retrieve top N players from the leaderboard (highest scores first)
    public Set<TypedTuple<String>> getTopPlayers(String gameId, long limit) {
        String key = getLeaderboardKey(gameId);
        Set<TypedTuple<String>> topPlayers = redisTemplate.opsForZSet().reverseRangeWithScores(key, 0, limit - 1);
        return topPlayers;
    }

    // Get a user's rank (starting at 1)
    public Long getUserRank(String gameId, String userId) {
        String key = getLeaderboardKey(gameId);
        Long rank = redisTemplate.opsForZSet().reverseRank(key, userId);
        if (rank != null) {
            return rank + 1;
        }
        return null;
    }
}
