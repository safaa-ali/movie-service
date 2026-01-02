package com.example.movie.controller;


import com.example.movie.models.Showtime;
import com.example.movie.models.dto.MovieRequestDTO;
import com.example.movie.models.enums.Genre;
import com.example.movie.service.MovieService;
import com.example.movie.models.Movie;
import com.example.movie.repo.MovieRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieRepository movieRepository;

    private final String VIDEO_ROOT = "D:/safaa-abdelsattar-projects/trailers/";

    // ===== 2️⃣ Upload trailer for a movie =====
    @PostMapping("/{id}/trailer/upload")
    public ResponseEntity<String> uploadTrailer(@PathVariable Long id,
                                                @RequestParam("file") MultipartFile file) throws IOException {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        String filename = file.getOriginalFilename();
        Path uploadPath = Paths.get(VIDEO_ROOT + filename);

        // Save file to local folder
        Files.copy(file.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);

        // Update movie with trailer path
        movie.setTrailerPath("/" + filename);
        movieRepository.save(movie);

        return ResponseEntity.ok("Trailer uploaded successfully");
    }


    // ===== Optional: Get movie info =====
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable Long id) {

        Movie movie = movieService.getMovieById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));;
        return ResponseEntity.ok(movie);
    }


    // ===== Optional: Get movies info =====
    @GetMapping("")
    public ResponseEntity<Optional<List<Movie>>> getMovie() {

        Optional<List<Movie>> movies = movieService.getAllMovies();
        return ResponseEntity.ok(movies);
    }


    @PostMapping(value = "/add-movie", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Movie addMovie(
            @RequestParam("movie") String movieJson, // receive JSON as string
            @RequestPart(value = "trailer", required = false) MultipartFile trailer
    ) throws IOException {
        // manually deserialize
        ObjectMapper mapper = new ObjectMapper();

        mapper.registerModule(new JavaTimeModule());
        // Optional: prevent failure on unknown properties
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        MovieRequestDTO movieRequest = mapper.readValue(movieJson, MovieRequestDTO.class);
        return movieService.addMovie(movieRequest, trailer);
    }



}


