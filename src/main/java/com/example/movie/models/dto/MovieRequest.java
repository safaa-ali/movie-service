package com.example.movie.models.dto;

import java.time.LocalDate;
import java.util.List;

public class MovieRequest {
    private String title;
    private String description;
    private LocalDate releaseDate;
    private Integer durationMinutes;
    private Double rating;
    private List<ShowTimeRequest> showTimes;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public List<ShowTimeRequest> getShowTimes() {
        return showTimes;
    }

    public void setShowTimes(List<ShowTimeRequest> showTimes) {
        this.showTimes = showTimes;
    }
}


