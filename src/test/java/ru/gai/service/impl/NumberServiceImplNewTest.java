package ru.gai.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import ru.gai.entity.Numeric;
import ru.gai.repository.NumericRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class NumberServiceImplNewTest {

    @InjectMocks
    private NumberServiceImplNew numberService;

    @Mock
    private NumericRepository numericRepository;

    @Test
    public void testRandom() {
        List<Numeric> numerics = new ArrayList<>();
        numerics.add(Numeric.builder().id(1L).word("ААА").numeric(2).build());
        numerics.add(Numeric.builder().id(4L).word("ААВ").numeric(89).build());
        numerics.add(Numeric.builder().id(10L).word("АВА").numeric(501).build());

        List<String> numbers = new ArrayList<>();
        numbers.add("А002АА 116 RUS");
        numbers.add("А089АВ 116 RUS");
        numbers.add("А501ВА 116 RUS");

        Numeric lastNumeric =  Numeric.builder().id(5L).word("АВА").numeric(501).build();

        Mockito.when(numericRepository.findAllByDateIssuedIsNullOrderById()).thenReturn(numerics);
        Mockito.when(numericRepository.findTopByDateIssuedIsNotNullOrderByDateIssuedDesc()).thenReturn(Optional.of(lastNumeric));

        numberService.init();

        String numberActual = numberService.next();
        Assert.assertTrue(numbers.contains(numberActual));
    }

    @Test
    public void testNext() {
        List<Numeric> numerics = new ArrayList<>();
        numerics.add(Numeric.builder().id(1L).word("ААА").numeric(2).build());
        numerics.add(Numeric.builder().id(4L).word("ААВ").numeric(89).build());
        numerics.add(Numeric.builder().id(10L).word("АВА").numeric(501).build());

        Numeric lastNumeric =  Numeric.builder().id(5L).word("АВА").numeric(501).build();

        Mockito.when(numericRepository.findAllByDateIssuedIsNullOrderById()).thenReturn(numerics);
        Mockito.when(numericRepository.findTopByDateIssuedIsNotNullOrderByDateIssuedDesc()).thenReturn(Optional.of(lastNumeric));
        numberService.init();

        String numberExpected = "А501ВА 116 RUS";
        String numberActual = numberService.next();

        Assert.assertEquals(numberExpected, numberActual);
    }

    @Test
    public void testNextStartApplication() {
        List<Numeric> numerics = new ArrayList<>();
        numerics.add(Numeric.builder().id(1L).word("ААА").numeric(2).build());
        numerics.add(Numeric.builder().id(4L).word("ААВ").numeric(89).build());
        numerics.add(Numeric.builder().id(10L).word("АВА").numeric(501).build());

        Mockito.when(numericRepository.findAllByDateIssuedIsNullOrderById()).thenReturn(numerics);
        Mockito.when(numericRepository.findTopByDateIssuedIsNotNullOrderByDateIssuedDesc()).thenReturn(Optional.empty());
        numberService.init();

        String numberExpected = "А002АА 116 RUS";
        String numberActual = numberService.next();

        Assert.assertEquals(numberExpected, numberActual);
    }

    @Test public void testNextNumberFinished() {
        List<Numeric> numerics = new ArrayList<>();

        Mockito.when(numericRepository.findAllByDateIssuedIsNullOrderById()).thenReturn(numerics);
        Mockito.verify(numericRepository, Mockito.never()).findTopByDateIssuedIsNotNullOrderByDateIssuedDesc();
        numberService.init();

        String numberExpected = "Номера закончились";
        String numberActual = numberService.next();
        Assert.assertEquals(numberExpected, numberActual);
    }
}