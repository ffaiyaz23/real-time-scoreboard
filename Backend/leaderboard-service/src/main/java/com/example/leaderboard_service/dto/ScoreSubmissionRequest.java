package com.example.leaderboard_service.dto;

public class ScoreSubmissionRequest {
    private String userId;
    private double score;

    public ScoreSubmissionRequest() {
    }

    public ScoreSubmissionRequest(String userId, double score) {
        this.userId = userId;
        this.score = score;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
