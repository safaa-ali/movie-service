package com.example.movie.repo;

import com.example.movie.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("SELECT DISTINCT m FROM Movie m LEFT JOIN FETCH m.showTimes")
    List<Movie> findAllWithShowTimes();

}