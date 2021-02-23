package com.dariuszpiotrkowski.magazineSystem.service;

import com.dariuszpiotrkowski.magazineSystem.dao.PcbRepository;
import com.dariuszpiotrkowski.magazineSystem.entity.Pcb;
import com.dariuszpiotrkowski.magazineSystem.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PcbServiceImpl implements PcbService {

    @Autowired
    private PcbRepository pcbRepository;

    @Autowired
    private ProjectsService projectsService;

    @Override
    public List<Pcb> findAll() {
        return pcbRepository.findAll();
    }

    @Override
    public Pcb findById(int id) {
        Optional<Pcb> result = pcbRepository.findById(id);

        Pcb pcb = null;

        if(result.isPresent()) {
            pcb = result.get();
        }
        else {
            throw new RuntimeException("Nie znaleziono PCB o id = " + id);
        }

        return pcb;
    }

    @Override
    public void save(Pcb pcb) {
        pcbRepository.save(pcb);
    }

    @Override
    public boolean deleteById(int id) {

        Optional<Pcb> pcb = pcbRepository.findById(id);

        if (pcb.isPresent()){
            List<Project> projects = projectsService.findAll();
            for (Project project : projects) {
                if(project.getPcb().getId() == id){
                    return false;
                }
            }
        }

        pcbRepository.deleteById(id);
        return true;
    }
}
