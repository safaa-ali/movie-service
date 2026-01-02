package com.example.movie.service;

import com.example.movie.models.Movie;
import com.example.movie.models.Showtime;
import com.example.movie.models.dto.MovieRequestDTO;
import com.example.movie.models.dto.ShowtimeDTO;
import com.example.movie.models.enums.Genre;
import com.example.movie.repo.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalTime;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MovieService {
    @Autowired
    private  MovieRepository movieRepository;
    @Autowired
    private  TrailerService trailerService;
    public Optional<Movie> getMovieById(Long id) {
            return movieRepository.findById(id);
    }


    public Optional<List<Movie>> getAllMovies() {
        return Optional.of(movieRepository.findAllWithShowTimes());
    }

    public Movie addMovie(MovieRequestDTO dto, MultipartFile trailer) throws IOException {
        Movie movie = new Movie();
        movie.setTitle(dto.getTitle());
        movie.setDescription(dto.getDescription());
        movie.setReleaseDate(dto.getReleaseDate());
        movie.setDurationMinutes(dto.getDuration());
        movie.setRating(dto.getRating());

        // Handle genre
        if (dto.getGenre() != null) {
            movie.setGenre(dto.getGenre()); // if DTO has Genre enum
            // OR: movie.setGenre(Genre.valueOf(dto.getGenre().toUpperCase())); if DTO has String
        }

        // Handle showtimes
        if (dto.getShowTimes() != null) {
            for (ShowtimeDTO s : dto.getShowTimes()) {
                Showtime showtime = new Showtime();
                showtime.setShowTime(LocalTime.parse(s.getShowTime()));
                movie.addShowtime(showtime); // sets movie and adds to list
            }
        }

        // Handle trailer file
        if (trailer != null && !trailer.isEmpty()) {
            String folder = "C:/movies/trailers/";
            String fileName = UUID.randomUUID() + "_" + trailer.getOriginalFilename();
            File dest = new File(folder + fileName);
            dest.getParentFile().mkdirs();
            trailer.transferTo(dest);

            movie.setTrailerPath("/trailers/" + fileName);
        }

        // Save movie (cascades to showtimes)
        return movieRepository.save(movie);
    }

}
