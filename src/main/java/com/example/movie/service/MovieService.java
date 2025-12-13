package com.example.movie.service;

import com.example.movie.models.Movie;
import com.example.movie.models.Showtime;
import com.example.movie.models.dto.MovieRequest;
import com.example.movie.models.dto.ShowTimeRequest;
import com.example.movie.repo.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalTime;

@Service
public class MovieService {
    @Autowired
    private  MovieRepository movieRepository;

    public Movie addMovie(MovieRequest dto) {
        Movie movie = new Movie();
        movie.setTitle(dto.getTitle());
        movie.setDescription(dto.getDescription());
        movie.setReleaseDate(dto.getReleaseDate());
        movie.setDurationMinutes(dto.getDurationMinutes());
        movie.setRating(dto.getRating());

        if (dto.getShowTimes() != null) {
            for (ShowTimeRequest stDto : dto.getShowTimes()) {
                Showtime showtime = new Showtime();
                showtime.setShowTime(LocalTime.parse(stDto.getShowTime())); // "21:00" -> LocalTime
                movie.addShowtime(showtime); // sets movie automatically
            }

        }

       return  movieRepository.save(movie);
    }
}
