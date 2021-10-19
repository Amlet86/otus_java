package ru.amlet.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MappingController {

    @RequestMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/clients")
    public String clientsListView(Model model) {
        return "clients";
    }

}
