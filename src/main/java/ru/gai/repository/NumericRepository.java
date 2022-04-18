package ru.gai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gai.entity.Numeric;

import java.util.List;
import java.util.Optional;

public interface NumericRepository extends JpaRepository<Numeric, Long> {

    /**
     * Получает все Номера у которых нет даты выдачи и отсортированные по id
     * @return список Номеров
     */
    List<Numeric> findAllByDateIssuedIsNullOrderById();

    /**
     * Получет первый Номер из списка Номеров,
     * у которых есть дата выдачи и отсортированнные по нему от большего к меньшему
     * @return Номер
     */
    Optional<Numeric> findTopByDateIssuedIsNotNullOrderByDateIssuedDesc();
}
