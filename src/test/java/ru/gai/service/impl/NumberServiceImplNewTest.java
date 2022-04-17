package ru.gai.service.impl;

import liquibase.pro.packaged.A;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import ru.gai.entity.Number;
import ru.gai.entity.Numeric;
import ru.gai.repository.NumberRepository;
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
        Numeric numeric1 = new Numeric(1L, "ААА", 2, false, true);
        Numeric numeric2 = new Numeric(2L, "ААВ", 3, true, false);
        Numeric numeric3 = new Numeric(3L, "ААЕ", 1, true, false);
        List<Numeric> numerics = new ArrayList<>();
        numerics.add(numeric1);
        numerics.add(numeric2);
        numerics.add(numeric3);
        Mockito.when(numericRepository.findAllByIssuedOrderById(false)).thenReturn(numerics);
        numberService.init();
        String numberExpected = "А002АА 116 RUS";
        String numberActual = numberService.next();
        Assert.assertEquals(numberExpected, numberActual);
    }

    @Test
    public void testNext() {
        Numeric numeric1 = new Numeric(1L, "ААА", 2, false, false);
        Numeric numeric2 = new Numeric(2L, "ААВ", 3, false, true);
        Numeric numeric3 = new Numeric(3L, "ААЕ", 1, false, false);
        List<Numeric> numerics = new ArrayList<>();
        numerics.add(numeric1);
        numerics.add(numeric2);
        numerics.add(numeric3);
        Mockito.when(numericRepository.findAllByIssuedOrderById(false)).thenReturn(numerics);
        numberService.init();
        String numberExpected = "А003АВ 116 RUS";
        String numberActual = numberService.next();
        Assert.assertEquals(numberExpected, numberActual);
    }

    @Test public void testNextNumberFinished() {
        List<Numeric> numerics = new ArrayList<>();
        Mockito.when(numericRepository.findAllByIssuedOrderById(false)).thenReturn(numerics);
        numberService.init();
        String numberExpected = "Номера закончились";
        String numberActual = numberService.next();
        Assert.assertEquals(numberExpected, numberActual);
    }
}