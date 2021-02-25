package com.dariuszpiotrkowski.magazineSystem;

import com.dariuszpiotrkowski.magazineSystem.Custom.FieldMatch;
import com.dariuszpiotrkowski.magazineSystem.dao.ElementRepository;
import com.dariuszpiotrkowski.magazineSystem.entity.Element;
import com.dariuszpiotrkowski.magazineSystem.service.ElementServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ElementServiceTest {

    @Mock
    private ElementRepository elementRepository;

    @InjectMocks
    private ElementServiceImpl elementService;

    @Test
    public void shouldReturnElementById() {

        int id = 1;
        Element element = new Element(id, "ABC", "", "", 100);

        when(elementRepository.findById(id)).thenReturn(Optional.of(element));

        assertNotNull(elementService.findById(id));
    }

    @Test
    public void shouldReturnRuntimeException() {

        int id = 1;
        Element element = new Element(id, "", "", "", 100);

        when(elementRepository.findById(id)).thenReturn(Optional.of(element));

        assertThrows(RuntimeException.class, () -> elementService.findById(2));
    }

    @Test
    public void shouldReturnListOfElements() {

        Element element1 = new Element(1, "ABC", "", "", 100);
        Element element2 = new Element(2, "DEF", "", "", 200);
        List<Element> list = new ArrayList<>();
        list.add(element1);
        list.add(element2);

        when(elementRepository.findAll()).thenReturn(list);

        assertEquals(2, elementService.findAll().size());
    }

}
