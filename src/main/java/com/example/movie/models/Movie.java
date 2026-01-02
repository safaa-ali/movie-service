package com.example.movie.models;


import com.example.movie.models.enums.Genre;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movie_id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(nullable = false)
    private Integer durationMinutes;
    @Enumerated(EnumType.STRING) // ✅ IMPORTANT
    private Genre genre;

    @Column
    private Double rating;
    @Column(name = "trailer_Path")
    private String trailerPath;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)

    @JsonManagedReference
    private List<Showtime> showTimes = new ArrayList<>();


    public Long getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(Long movie_id) {
        this.movie_id = movie_id;
    }

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

    public String getTrailerPath() {
        return trailerPath;
    }

    public void setTrailerPath(String trailerPath) {
        this.trailerPath = trailerPath;
    }

    public List<Showtime> getShowTimes() {
        return showTimes;
    }

//    public void setShowTimes(List<Showtime> showTimes) {
//        this.showTimes = showTimes;
//      //  this.showTimes.set
//    }


    public void setShowTimes(List<Showtime> showTimes) {
        this.showTimes.clear();
        if (showTimes != null) {
            for (Showtime showtime : showTimes) {
                addShowtime(showtime);
            }
        }
    }

    public void addShowtime(Showtime showtime) {

        showTimes.add(showtime);
        showtime.setMovie(this); // Important: sets the foreign key
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}


