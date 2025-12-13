package com.example.movie.controller;


import com.example.movie.models.Showtime;
import com.example.movie.models.dto.ShowTimeRequest;
import com.example.movie.models.dto.MovieRequest;
import com.example.movie.service.MovieService;
import com.example.movie.models.Movie;
import com.example.movie.repo.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalTime;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieRepository movieRepository;

    private final String VIDEO_ROOT = "D:/safaa-abdelsattar-projects/trailers/";

    // ===== 1️⃣ Create a new movie =====
    @PostMapping("/add-movie")
    public ResponseEntity<Movie> addMovie(@RequestBody MovieRequest movie) {
        Movie savedMovie = movieService.addMovie(movie);
        return ResponseEntity.ok(savedMovie);
    }

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

    // ===== 3️⃣ Stream trailer video =====
    @GetMapping("/{id}/trailer")
    public ResponseEntity<Resource> getTrailer(@PathVariable Long id) throws MalformedURLException {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        Path videoPath = Paths.get(VIDEO_ROOT + movie.getTrailerPath());
        Resource resource = new UrlResource(videoPath.toUri());

        if (!resource.exists()) {
            throw new RuntimeException("File not found");
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "video/mp4")
                .body(resource);
    }

    // ===== Optional: Get movie info =====
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        return ResponseEntity.ok(movie);
    }
}
