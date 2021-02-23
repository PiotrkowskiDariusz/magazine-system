package com.dariuszpiotrkowski.magazineSystem.service;

import com.dariuszpiotrkowski.magazineSystem.dao.ElementRepository;
import com.dariuszpiotrkowski.magazineSystem.entity.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ElementServiceImpl implements ElementService {

    private ElementRepository elementRepository;

    @Autowired
    public ElementServiceImpl(ElementRepository elementRepository) {
        this.elementRepository = elementRepository;
    }


    @Override
    public List<Element> findAll() {
        return elementRepository.findAll();
    }

    @Override
    public Element findById(int id) {
        Optional<Element> result = elementRepository.findById(id);

        Element element = null;

        if (result.isPresent()) {
            element = result.get();
        }
        else {
            throw new RuntimeException("Nie znaleziono elementu o id = " + id);
        }

        return element;
    }

    @Override
    public Element findByPartNumber(String partNumber) {
        return elementRepository.findByPartNumber(partNumber);
    }

    @Override
    public void save(Element element) {
        elementRepository.save(element);
    }

    @Override
    public void deleteById(int id) {
        elementRepository.deleteById(id);
    }

    @Override
    public List<Element> searchByPartNumber(String partNumber) {

        List<Element> results = null;

        if (partNumber != null && (partNumber.trim().length() > 0)) {
            results = elementRepository.findByPartNumberContainsAllIgnoreCase(partNumber);
        }
        else {
            results = findAll();
        }

        return results;
    }
}
