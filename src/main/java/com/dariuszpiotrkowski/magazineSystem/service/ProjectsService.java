package com.dariuszpiotrkowski.magazineSystem.service;

import com.dariuszpiotrkowski.magazineSystem.entity.Project;

import java.util.List;

public interface ProjectsService {

    public List<Project> findAll();

    public Project findById(int id);

    public void save(Project project);

    public boolean deleteById(int id);

    public List<Project> searchByName(String name);

}
