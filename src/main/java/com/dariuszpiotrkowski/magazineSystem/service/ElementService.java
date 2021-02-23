package com.dariuszpiotrkowski.magazineSystem.service;

import com.dariuszpiotrkowski.magazineSystem.entity.Element;

import java.util.List;

public interface ElementService {

    public List<Element> findAll();

    public Element findById(int id);

    public Element findByPartNumber(String partNumber);

    public void save(Element element);

    public void deleteById(int id);

    public List<Element> searchByPartNumber(String partNumber);

}
