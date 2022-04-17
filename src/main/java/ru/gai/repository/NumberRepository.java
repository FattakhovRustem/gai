package ru.gai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gai.entity.Number;

import java.util.Optional;

public interface NumberRepository extends JpaRepository<Number, Long> {

    /**
     * Получает последний добавленный элемент(Номер)
     * @return
     */
    Optional<Number> findTopByOrderByIdDesc();

    /**
     * Получает Номер, соответсвтующий word и numeric
     * @param word комбинация букв ААА
     * @param numeric число из Номера
     * @return
     */
    Optional<Number> findByWordAndNumeric(String word, Integer numeric);
}
