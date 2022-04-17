package ru.gai.service;

public interface NumberService {

    /**
     * Генерирует рандомный Номер
     * @return Номер АХХХАА 116 RUS
     */
    String random();

    /**
     * Генерирует Номер, который следует за выданным ранее
     * @return Номер АХХХАА 116 RUS
     */
    String next();
}
