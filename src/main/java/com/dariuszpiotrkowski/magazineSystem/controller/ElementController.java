package com.dariuszpiotrkowski.magazineSystem.controller;

import com.dariuszpiotrkowski.magazineSystem.entity.Element;
import com.dariuszpiotrkowski.magazineSystem.service.ElementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/elements")
public class ElementController {

    private ElementService elementService;

    @Autowired
    public ElementController(ElementService elementService) {
        this.elementService = elementService;
    }

    @GetMapping("/list")
    public String listElements(Model model) {

        List<Element> elements = elementService.findAll();

        model.addAttribute("elements", elements);

        return "/elements/list-elements";
    }

    @GetMapping("/addElement")
    public String addElement(Model model) {

        Element element = new Element();

        model.addAttribute("element", element);

        return "/elements/element-form";
    }

    @PostMapping("/saveElement")
    public String saveElement(@Valid @ModelAttribute("element") Element element,
                              BindingResult bindingResult,
                              Model model) {

        if(bindingResult.hasErrors()) {
            return "/elements/element-form";
        }

        Element existing = elementService.findByPartNumber(element.getPartNumber());
        if (existing != null && existing.getId() != element.getId()) {
            model.addAttribute("element", new Element());
            model.addAttribute("elementError", "Komponent o tym numerze już istnieje.");
            return "/elements/element-form";
        }

        elementService.save(element);

        return "redirect:/elements/list";
    }

    @GetMapping("/updateElement")
    public String updateElement(@ModelAttribute("elementId") int id, Model model) {

        Element element = elementService.findById(id);

        model.addAttribute("element", element);

        return "/elements/element-form";
    }

    @GetMapping("/deleteElement")
    public String deleteElement(@ModelAttribute("elementId") int id) {

        elementService.deleteById(id);

        return "redirect:/elements/list";
    }

    @GetMapping("/search")
    public String search(@ModelAttribute("partNumber") String partNumber, Model model) {

        List<Element> elements = elementService.searchByPartNumber(partNumber);

        model.addAttribute("elements", elements);

        return "/elements/list-elements";
    }

}
