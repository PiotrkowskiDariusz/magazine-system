package com.dariuszpiotrkowski.magazineSystem.controller;

import com.dariuszpiotrkowski.magazineSystem.Custom.SystemUser;
import com.dariuszpiotrkowski.magazineSystem.entity.Role;
import com.dariuszpiotrkowski.magazineSystem.entity.User;
import com.dariuszpiotrkowski.magazineSystem.service.RoleService;
import com.dariuszpiotrkowski.magazineSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/list")
    public String listUsers(Model model) {

        List<User> users = userService.findAll();

        model.addAttribute("users", users);

        return "registration/list-users";
    }

    @GetMapping("/showRegistrationForm")
    public String showRegistrationForm(Model model) {

        List<Role> roles = roleService.findAll();

        model.addAttribute("systemUser", new SystemUser());
        model.addAttribute("roles", roles);

        return "registration/registration-form";
    }

    @PostMapping("/processRegistrationForm")
    public String proccessRegistrationForm(@Valid @ModelAttribute("systemUser") SystemUser systemUser,
                                           BindingResult theBindingResult,
                                           Model model) {
        String userName = systemUser.getUserName();

        if (theBindingResult.hasErrors()) {
            model.addAttribute("roles", roleService.findAll());
            return "registration/registration-form";
        }

        User existing = userService.findByUserName(userName);
        if (existing != null) {
            model.addAttribute("systemUser", new SystemUser());
            model.addAttribute("roles", roleService.findAll());
            model.addAttribute("registrationError", "Użytkownik o tej nazwie już istnieje.");

            return "registration/registration-form";
        }

        userService.save(systemUser);

        return "registration/registration-confirmation";
    }

    @GetMapping("/updateUser")
    public String updateUser(@ModelAttribute("userId") int userId, Model model) {

        User user = userService.findById(userId);
        List<Role> roles = roleService.findAll();

        model.addAttribute("user", user);
        model.addAttribute("roles", roles);

        return "registration/update-form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("user") User user) {

        userService.update(user);

        return "redirect:/register/list";
    }

    @GetMapping("/deleteUser")
    public String deleteUser(@ModelAttribute("userId") int userId) {

        userService.deleteById(userId);

        return "redirect:/register/list";
    }

    @GetMapping("/updatePassword")
    public String changePassword(@RequestParam("userId") int userId, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user;
        if (!(authentication instanceof AnonymousAuthenticationToken) && (userId == 0)) {
            String userName = authentication.getName();
            user = userService.findByUserName(userName);
        }
        else {
            user = userService.findById(userId);
        }

        SystemUser systemUser = new SystemUser();
        systemUser.setUserName(user.getUserName());
        systemUser.setPassword(user.getPassword());
        systemUser.setMatchingPassword(user.getPassword());
        systemUser.setFirstName(user.getFirstName());
        systemUser.setLastName(user.getLastName());
        systemUser.setEmail(user.getEmail());

        List<Role> list = new ArrayList<>(user.getRoles());
        systemUser.setRoles(list);

        model.addAttribute("userId", user.getId());
        model.addAttribute("systemUser", systemUser);

        return "registration/password-form";
    }

    @PostMapping("/savePassword")
    public String savePassword(@Valid @ModelAttribute("systemUser") SystemUser systemUser,
                               BindingResult theBindingResult,
                               @RequestParam("userId") int userId,
                               Model model) {

        if (theBindingResult.hasErrors()) {
            model.addAttribute("userId", userId);
            return "registration/password-form";
        }

        User existing = userService.findByUserName(systemUser.getUserName());
        if (existing != null && existing.getId() != userId) {
            model.addAttribute("systemUser", systemUser);
            model.addAttribute("userId", userId);
            model.addAttribute("registrationError", "Użytkownik o tej nazwie już istnieje.");
            return "registration/password-form";
        }

        User user = userService.findById(userId);

        user.setUserName(systemUser.getUserName());
        user.setPassword(passwordEncoder.encode(systemUser.getPassword()));

        userService.update(user);

        return "redirect:/";
    }

}
