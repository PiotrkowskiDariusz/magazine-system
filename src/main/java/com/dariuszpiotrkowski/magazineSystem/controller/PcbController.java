package com.dariuszpiotrkowski.magazineSystem.controller;

import com.dariuszpiotrkowski.magazineSystem.entity.Pcb;
import com.dariuszpiotrkowski.magazineSystem.service.PcbService;
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
@RequestMapping("/pcb")
public class PcbController {

    @Autowired
    private PcbService pcbService;

    @GetMapping("/list")
    public String listPcb(Model model) {

        List<Pcb> pcbs = pcbService.findAll();

        model.addAttribute("pcbs", pcbs);

        return "pcb/list-pcb";
    }

    @GetMapping("/addPcb")
    public String addPcb (Model model) {

        Pcb pcb = new Pcb();

        model.addAttribute("pcb", pcb);

        return "pcb/pcb-form";
    }

    @GetMapping("/updatePcb")
    public String updatePcb(@ModelAttribute("pcbId") int id, Model model) {

        Pcb pcb = pcbService.findById(id);

        model.addAttribute(pcb);

        return "pcb/pcb-form";
    }

    @PostMapping("/savePcb")
    public String savePcb(@ModelAttribute("pcb") Pcb pcb) {

        if (pcb.getFilePath().length() == 0) {
            pcb.setFilePath(null);
        }

        pcbService.save(pcb);

        return "redirect:/pcb/list";
    }

    @GetMapping("/addFile")
    public String addFile(@ModelAttribute("pcbId") int id, Model model) {

        Pcb pcb = pcbService.findById(id);

        model.addAttribute("pcb", pcb);

        return "pcb/file-form";
    }

    @PostMapping("/saveFile")
    public String saveFile(@RequestParam("pcbId") int id, @RequestParam("file") MultipartFile multipartFile) {

        Pcb pcb = pcbService.findById(id);
        String path = "D:\\MagazineSystemPliki\\pcb\\" + StringUtils.cleanPath(multipartFile.getOriginalFilename());

        File file = new File(path);
        try {
            FileOutputStream os = new FileOutputStream(file);
            os.write(multipartFile.getBytes());
            os.close();
        }
        catch (IOException e) {
            System.out.println("Blad pliku");
        }

        if (pcb.getFilePath() != null) {
            File oldFile = new File(pcb.getFilePath());
            oldFile.delete();
        }

        pcb.setFilePath(path);

        pcbService.save(pcb);

        return "redirect:/pcb/list";
    }

    @GetMapping("/downloadFile/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable int id) {

        Pcb pcb = pcbService.findById(id);
        File file = new File(pcb.getFilePath());

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

    @GetMapping("/deletePcb")
    public String deletePcb(@ModelAttribute("pcbId") int id, Model model) {

        Pcb pcb = pcbService.findById(id);

        if (pcb.getFilePath() != null) {
            File oldFile = new File(pcb.getFilePath());
            oldFile.delete();
        }

        if (pcbService.deleteById(id) == true) {
            return "redirect:/pcb/list";
        }
        else {
            model.addAttribute("deleteError", "Nie można usunąć PCB. Istnieją powiązane elementy podrzędne.");
            model.addAttribute("pcbs", pcbService.findAll());
            return "/pcb/list-pcb";
        }
    }

}
