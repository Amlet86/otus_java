package ru.amlet.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.amlet.dao.ClientDAO;
import ru.amlet.services.ClientService;

@Controller
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/clients")
    public String clientsListView(Model model) {
        List<ClientDAO> clients = clientService.findAll();
        model.addAttribute("clients", clients);
        return "clients";
    }

}
