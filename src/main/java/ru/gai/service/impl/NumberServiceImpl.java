package ru.gai.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gai.config.Constant;
import ru.gai.entity.Number;
import ru.gai.repository.NumberRepository;
import ru.gai.service.NumberService;

@Service("number-service")
public class NumberServiceImpl implements NumberService {

    private final char[] letters = new char[] {'А', 'В', 'Е', 'К', 'М', 'Н', 'O', 'Р', 'С', 'Т', 'У', 'Х'};

    private final NumberRepository numberRepository;

    public NumberServiceImpl(NumberRepository numberRepository) {
        this.numberRepository = numberRepository;
    }

    @Transactional
    public String random() {
        while (true) {
            char[] numberLetters = new char[3];

            for (int i = 0; i < numberLetters.length; i++) {
                numberLetters[i] = letters[(int) (Math.random() * 12)];
            }
            String wordFromLetters = new String(numberLetters);
            int numberNumeric = 1 + (int) (Math.random() * 999);

            if (numberRepository.findByWordAndNumeric(wordFromLetters , numberNumeric).isEmpty()) {
                Number saveNumber = new Number(wordFromLetters, numberNumeric);
                numberRepository.save(saveNumber);
                return String.format("%c%03d%c%c ",
                        numberLetters[0],
                        numberNumeric,
                        numberLetters[1],
                        numberLetters[2]) + Constant.REGION;
            }
        }
    }

    @Transactional
    public String next() {
        while(true) {
            Number lastNumber = numberRepository.findTopByOrderByIdDesc()
                    .orElse(new Number("ААА", 0));

            Number saveNumber = new Number();
            if (lastNumber.getNumeric() == 999) {
                char[] charsWord = lastNumber.getWord().toCharArray();
                String result = incrementWord(charsWord, 2);
                saveNumber.setNumeric(1);
                saveNumber.setWord(result);
            } else {
                saveNumber.setNumeric(lastNumber.getNumeric() + 1);
                saveNumber.setWord(lastNumber.getWord());
            }

            if (numberRepository.findByWordAndNumeric(saveNumber.getWord(), saveNumber.getNumeric()).isEmpty()) {
                numberRepository.save(saveNumber);
                char[] numberLetters = saveNumber.getWord().toCharArray();
                return String.format("%c%03d%c%c ",
                        numberLetters[0],
                        saveNumber.getNumeric(),
                        numberLetters[1],
                        numberLetters[2]) + Constant.REGION;
            }
        }
    }

    /**
     * Возвращает следующую комбинацию букв после charsWord
     * @param charsWord буквы из Номера
     * @param n с какой буквы идет поиск
     * @return
     */
    private String incrementWord(char[] charsWord, int n) {
        for(int i = 0; i < letters.length; i++) {
            if (charsWord[n] == letters[i]) {
                if (i < letters.length - 1) {
                    charsWord[n] = letters[i + 1] ;
                    return new String(charsWord);
                } else {
                    charsWord[n] = letters[0];
                    return incrementWord(charsWord, n - 1);
                }
            }
        }
        return "";
    }
}
