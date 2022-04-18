package ru.gai.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gai.config.Constant;
import ru.gai.entity.Numeric;
import ru.gai.repository.NumericRepository;
import ru.gai.service.NumberService;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.*;

@Service("number-service-new")
public class NumberServiceImplNew implements NumberService {

    private final NumericRepository numericRepository;

    private List<Numeric> cacheNumeric;
    private int nextIndex;

    public NumberServiceImplNew(NumericRepository numericRepository) {
        this.numericRepository = numericRepository;
    }

    @PostConstruct
    public void init() {
        this.cacheNumeric = numericRepository.findAllByDateIssuedIsNullOrderById();

        if (this.cacheNumeric.isEmpty()) {
            return;
        }

        Optional<Numeric> numericOptional = numericRepository.findTopByDateIssuedIsNotNullOrderByDateIssuedDesc();
        if (numericOptional.isPresent()) {
            for (int i = 0; i < this.cacheNumeric.size(); i++) {
                if (this.cacheNumeric.get(i).getId() > numericOptional.get().getId()) {
                    this.nextIndex = i;
                    return;
                }
            }
        }

        this.nextIndex = 0;
    }

    @Transactional
    @Override
    public String random() {
        while (!cacheNumeric.isEmpty()) {
            int randomIndex = (int) (Math.random() * cacheNumeric.size());
            return getNumber(randomIndex);
        }
        return Constant.NUMBER_FINISHED;
    }

    @Transactional
    @Override
    public String next() {
        while (!cacheNumeric.isEmpty()) {
            return getNumber(this.nextIndex);
        }
        return Constant.NUMBER_FINISHED;
    }

    /**
     * Выдает Номер по индексу и назначает индекс следующего элемента по порядку
     * @param index индекс Номера
     * @return Номер в виде строки АХХХАА 116 RUS
     */
    private String getNumber(int index) {
        Numeric numeric = cacheNumeric.remove(index);
        numeric.setDateIssued(LocalDateTime.now());
        numericRepository.save(numeric);

        if (index == cacheNumeric.size()) {
            this.nextIndex = 0;
        } else {
            this.nextIndex = index;
        }

        char[] words = numeric.getWord().toCharArray();
        return String.format("%c%03d%c%c ", words[0], numeric.getNumeric(), words[1], words[2]) + Constant.REGION;
    }
}
