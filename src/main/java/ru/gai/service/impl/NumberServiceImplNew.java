package ru.gai.service.impl;

import org.springframework.stereotype.Service;
import ru.gai.config.Constant;
import ru.gai.entity.Numeric;
import ru.gai.repository.NumericRepository;
import ru.gai.service.NumberService;

import javax.annotation.PostConstruct;
import java.util.*;

@Service("number-service-new")
public class NumberServiceImplNew implements NumberService {

    private final NumericRepository numericRepository;

    private List<Numeric> cacheNumericLinked;
    private Numeric nextNumeric;
    private int nextIndex;

    public NumberServiceImplNew(NumericRepository numericRepository) {
        this.numericRepository = numericRepository;
    }

    @PostConstruct
    public void init() {
        this.cacheNumericLinked = numericRepository.findAllByIssuedOrderById(false);

        if (this.cacheNumericLinked.isEmpty()) {
            return;
        }

        for (int i = 0; i < this.cacheNumericLinked.size(); i++) {
            this.nextNumeric = cacheNumericLinked.get(i);
            if (this.nextNumeric.getNext()) {
                this.nextIndex = i;
                return;
            }
        }
        this.nextNumeric = cacheNumericLinked.get(0);
        this.nextIndex = 0;
    }

    @Override
    public String random() {
        while (!cacheNumericLinked.isEmpty()) {
            this.nextNumeric.setNext(false);
            numericRepository.save(this.nextNumeric);

            int randomIndex = (int) (Math.random() * cacheNumericLinked.size());
            Numeric randomValue = cacheNumericLinked.get(randomIndex);
            randomValue.setIssued(true);
            numericRepository.save(randomValue);
            cacheNumericLinked.remove(randomIndex);

            if (randomIndex == cacheNumericLinked.size()) {
                this.nextIndex = 0;
            }
            this.nextNumeric = cacheNumericLinked.get(this.nextIndex);
            this.nextNumeric.setNext(true);
            numericRepository.save(this.nextNumeric);

            char[] words = randomValue.getWord().toCharArray();
            return String.format("%c%03d%c%c ", words[0], randomValue.getNumeric(), words[1], words[2]) + Constant.REGION;
        }
        return Constant.NUMBER_FINISHED;
    }

    @Override
    public String next() {
        while (!cacheNumericLinked.isEmpty()) {
            Numeric nextValue = cacheNumericLinked.get(this.nextIndex);
            nextValue.setIssued(true);
            nextValue.setNext(false);
            numericRepository.save(nextValue);
            cacheNumericLinked.remove(this.nextIndex);

            if (this.nextIndex == cacheNumericLinked.size()) {
                this.nextIndex = 0;
            }
            this.nextNumeric = cacheNumericLinked.get(this.nextIndex);
            this.nextNumeric.setNext(true);
            numericRepository.save(this.nextNumeric);

            char[] words = nextValue.getWord().toCharArray();
            return String.format("%c%03d%c%c ", words[0], nextValue.getNumeric(), words[1], words[2]) + Constant.REGION;
        }
        return Constant.NUMBER_FINISHED;
    }
}
