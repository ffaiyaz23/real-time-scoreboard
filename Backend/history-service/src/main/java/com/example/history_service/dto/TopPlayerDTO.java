package com.example.history_service.dto;

public class TopPlayerDTO {
    private String userId;
    private double totalScore;

    public TopPlayerDTO() {
    }

    public TopPlayerDTO(String userId, double totalScore) {
        this.userId = userId;
        this.totalScore = totalScore;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }
}
