package ru.gai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.gai.entity.Numeric;

import java.util.List;

public interface NumericRepository extends JpaRepository<Numeric, Long> {

    /**
     * Получает комбинации выданных/не выданных номеров
     * @param issued Номер выдан - true, Номер не выдан - false
     * @return Возвращает список номеров
     */
    List<Numeric> findAllByIssuedOrderById(Boolean issued);
}
