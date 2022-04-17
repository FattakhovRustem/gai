package ru.gai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gai.entity.Number;

import java.util.Optional;

public interface NumberRepository extends JpaRepository<Number, Long> {

    Optional<Number> findTopByOrderByIdDesc();

    Optional<Number> findByWordAndNumeric(String word, Integer numeric);
}
