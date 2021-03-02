package com.dariuszpiotrkowski.magazineSystem;

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
    public void should_return_element_by_id() {

        int id = 1;
        Element element = new Element(id, "ABC", "", "", 100);

        when(elementRepository.findById(id)).thenReturn(Optional.of(element));

        assertNotNull(elementService.findById(id));
    }

    @Test
    public void should_throw_runtime_exception() {

        int id = 1;
        Element element = new Element(id, "", "", "", 100);

        when(elementRepository.findById(id)).thenReturn(Optional.of(element));

        assertThrows(RuntimeException.class, () -> elementService.findById(2));
    }

    @Test
    public void should_return_list_of_elements_size_2() {

        Element element1 = new Element(1, "ABC", "", "", 100);
        Element element2 = new Element(2, "DEF", "", "", 200);
        List<Element> list = new ArrayList<>();
        list.add(element1);
        list.add(element2);

        when(elementRepository.findAll()).thenReturn(list);

        assertEquals(2, elementService.findAll().size());
    }

    @Test
    public void should_return_list_of_elements_size_3() {

        Element element1 = new Element(1, "ABC", "", "", 100);
        Element element2 = new Element(2, "DEF", "", "", 200);
        Element element3 = new Element(3, "XYZ", "", "", 200);
        List<Element> list = new ArrayList<>();
        list.add(element1);
        list.add(element2);
        list.add(element3);

        when(elementRepository.findAll()).thenReturn(list);

        assertEquals(3, elementService.findAll().size());
    }

    @Test
    public void should_return_elements_by_part_number() {

        Element element1 = new Element(1, "ABC", "", "", 100);
        Element element2 = new Element(2, "ABC", "", "", 200);
        List<Element> list = new ArrayList<>();
        list.add(element1);
        list.add(element2);

        when(elementRepository.findByPartNumberContainsAllIgnoreCase("ABC")).thenReturn(list);

        assertEquals(2, elementService.searchByPartNumber("ABC").size());
    }

}
