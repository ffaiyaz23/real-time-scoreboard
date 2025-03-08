package com.example.leaderboard_service.dto;

import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import java.util.Set;

public class LeaderboardResponse {
    private Set<TypedTuple<String>> leaderboard;

    public LeaderboardResponse() {
    }

    public LeaderboardResponse(Set<TypedTuple<String>> leaderboard) {
        this.leaderboard = leaderboard;
    }

    public Set<TypedTuple<String>> getLeaderboard() {
        return leaderboard;
    }

    public void setLeaderboard(Set<TypedTuple<String>> leaderboard) {
        this.leaderboard = leaderboard;
    }
}
