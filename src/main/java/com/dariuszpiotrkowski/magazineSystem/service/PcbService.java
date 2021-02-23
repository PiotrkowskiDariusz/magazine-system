package com.dariuszpiotrkowski.magazineSystem.service;

import com.dariuszpiotrkowski.magazineSystem.entity.Pcb;

import java.util.List;

public interface PcbService {

    public List<Pcb> findAll();

    public Pcb findById(int id);

    public void save(Pcb pcb);

    public boolean deleteById(int id);

}
