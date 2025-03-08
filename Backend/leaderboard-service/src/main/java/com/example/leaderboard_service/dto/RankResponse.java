package com.example.leaderboard_service.dto;

public class RankResponse {
    private Long rank;

    public RankResponse() {
    }

    public RankResponse(Long rank) {
        this.rank = rank;
    }

    public Long getRank() {
        return rank;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }
}
