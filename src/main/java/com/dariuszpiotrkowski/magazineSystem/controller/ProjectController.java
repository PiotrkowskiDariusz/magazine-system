package com.dariuszpiotrkowski.magazineSystem.controller;

import com.dariuszpiotrkowski.magazineSystem.entity.Pcb;
import com.dariuszpiotrkowski.magazineSystem.entity.Project;
import com.dariuszpiotrkowski.magazineSystem.service.PcbService;
import com.dariuszpiotrkowski.magazineSystem.service.ProjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectsService projectsService;

    @Autowired
    private PcbService pcbService;

    @GetMapping("/list")
    public String listProjects(Model model) {

        List<Project> projects = projectsService.findAll();

        model.addAttribute("projects", projects);

        return "/projects/list-projects";
    }

    @GetMapping("/addProject")
    public String addProject(Model model) {

        Project project = new Project();
        List<Pcb> pcbs = pcbService.findAll();

        model.addAttribute("project", project);
        model.addAttribute("pcbs", pcbs);

        return "/projects/project-form";
    }

    @PostMapping("/saveProject")
    public String saveProject(@ModelAttribute("project") Project project,
                              @RequestParam("pcbId") int pcbId,
                              Model model) {

        if (pcbId != 0) {
            Pcb pcb = pcbService.findById(pcbId);
            if (pcb.getProject() == null || pcb.getProject().getId() == project.getId()) {
                project.setPcb(pcb);
            }
            else {
                model.addAttribute("pcbError", "Wybrane PCB jest już przypisane do innego projektu.");
                return "/projects/project-form";
            }
        }

        if (project.getDocPath().length() == 0) {
            project.setDocPath(null);
        }

        projectsService.save(project);

        return "redirect:/projects/list";
    }

    @GetMapping("/updateProject")
    public String updateProject(@ModelAttribute("projectId") int projectId, Model model) {

        Project project = projectsService.findById(projectId);
        List<Pcb> pcbs = pcbService.findAll();

        model.addAttribute("project", project);
        model.addAttribute("pcbs", pcbs);

        return "/projects/project-form";
    }

    @GetMapping("/addFile")
    public String addFile(@ModelAttribute("projectId") int id, Model model) {

        Project project = projectsService.findById(id);

        model.addAttribute("project", project);

        return "/projects/file-form";
    }

    @PostMapping("/saveFile")
    public String saveFile(@RequestParam("projectId") int id, @RequestParam("file") MultipartFile multipartFile) {

        Project project = projectsService.findById(id);
        String path = "D:\\MagazineSystemPliki\\project\\" + StringUtils.cleanPath(multipartFile.getOriginalFilename());

        File file = new File(path);
        try {
            FileOutputStream os = new FileOutputStream(file);
            os.write(multipartFile.getBytes());
            os.close();
        }
        catch (IOException e) {
            System.out.println("Blad pliku");
        }

        if (project.getDocPath() != null) {
            File oldFile = new File(project.getDocPath());
            oldFile.delete();
        }

        project.setDocPath(path);

        projectsService.save(project);

        return "redirect:/projects/list";
    }

    @GetMapping("/downloadFile/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable int id) {

        Project project = projectsService.findById(id);
        File file = new File(project.getDocPath());

        Path path = Paths.get(file.getAbsolutePath());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        try {
            ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
            String mimeType = Files.probeContentType(file.toPath());
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(file.length())
                    .contentType(MediaType.parseMediaType(mimeType))
                    .body(resource);
        }
        catch (IOException e) {
            System.out.println("IOException");
        }

        return null;
    }

    @GetMapping("/deleteProject")
    public String deleteProject(@ModelAttribute("projectId") int id, Model model) {

        Project project = projectsService.findById(id);

        if (project.getDocPath() != null) {
            File oldFile = new File(project.getDocPath());
            oldFile.delete();
        }

        if (projectsService.deleteById(id) == true) {
            return "redirect:/projects/list";
        }
        else {
            model.addAttribute("deleteError", "Nie można usunąć projektu. Istnieją powiązane elementy podrzędne.");
            model.addAttribute("projects", projectsService.findAll());
            return "/projects/list-projects";
        }
    }

    @GetMapping("/search")
    public String search(@ModelAttribute("name") String name, Model model) {

        List<Project> projects = projectsService.searchByName(name);

        model.addAttribute("projects", projects);

        return "/projects/list-projects";
    }



}
