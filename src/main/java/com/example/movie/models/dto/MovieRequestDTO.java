package com.example.movie.models.dto;

import com.example.movie.models.enums.Genre;

import java.time.LocalDate;
import java.util.List;

public class MovieRequestDTO {
    private String title;
    private String description;
    private LocalDate releaseDate;
    private Integer durationMinutes;
    private Double rating;
    private List<ShowtimeDTO> showTimes;
    private Integer duration;
    private Genre genre; // Or Genre enum

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

    public List<ShowtimeDTO> getShowTimes() {
        return showTimes;
    }

    public void setShowTimes(List<ShowtimeDTO> showTimes) {
        this.showTimes = showTimes;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}


