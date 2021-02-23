package com.dariuszpiotrkowski.magazineSystem.dao;

import com.dariuszpiotrkowski.magazineSystem.entity.Element;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ElementRepository extends JpaRepository<Element, Integer> {

    public List<Element> findByPartNumberContainsAllIgnoreCase(String partNumber);

    public Element findByPartNumber(String partNumber);

}
