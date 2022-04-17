package ru.gai.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import ru.gai.entity.Number;
import ru.gai.repository.NumberRepository;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class NumberServiceImplTest {

    @InjectMocks
    private NumberServiceImpl numberService;

    @Mock
    private NumberRepository numberRepository;

    @Test
    public void testNext() {
        Number number = new Number("АТУ", 900);
        Mockito.when(numberRepository.findTopByOrderByIdDesc()).thenReturn(Optional.of(number));
        Mockito.when(numberRepository.findByWordAndNumeric(Mockito.any(), Mockito.any())).thenReturn(Optional.empty());
        String numberExpected = "А901ТУ 116 RUS";
        String numberActual = numberService.next();
        Assert.assertEquals(numberExpected, numberActual);
    }
}
