package com.dariuszpiotrkowski.magazineSystem.service;

import com.dariuszpiotrkowski.magazineSystem.dao.ProjectRepository;
import com.dariuszpiotrkowski.magazineSystem.entity.Order;
import com.dariuszpiotrkowski.magazineSystem.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectsServiceImpl implements ProjectsService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private OrderService orderService;

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public Project findById(int id) {
        Optional<Project> result = projectRepository.findById(id);

        Project project = null;

        if (result.isPresent()) {
            project = result.get();
        }
        else {
            throw new RuntimeException("Nie znaleziono projektu o id =  " + id);
        }

        return project;
    }

    @Override
    public void save(Project project) {
        projectRepository.save(project);
    }

    @Override
    public boolean deleteById(int id) {

        Optional<Project> project = projectRepository.findById(id);

        if (project.isPresent()){
            List<Order> orders = orderService.findAll();
            for (Order order : orders) {
                if(order.getProject().getId() == id){
                    return false;
                }
            }
        }

        projectRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Project> searchByName(String name) {
        List<Project> results = null;

        if (name != null && (name.trim().length() > 0)) {
            results = projectRepository.findByNameContainsAllIgnoreCase(name);
        }
        else {
            results = findAll();
        }

        return results;
    }
}
