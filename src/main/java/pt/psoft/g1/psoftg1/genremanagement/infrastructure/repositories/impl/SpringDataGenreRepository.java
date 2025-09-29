package pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import pt.psoft.g1.psoftg1.genremanagement.infrastructure.persistence.jpa.GenreJpa;

import java.util.Optional;

public interface SpringDataGenreRepository extends JpaRepository<GenreJpa, Long> {
    Optional<GenreJpa> findByGenre(String genre);
    void deleteByGenre(String genre);
}
