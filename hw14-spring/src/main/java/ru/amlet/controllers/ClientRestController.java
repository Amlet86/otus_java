package ru.amlet.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.amlet.dao.Client;
import ru.amlet.dto.ClientDTO;
import ru.amlet.services.ClientService;

@RestController
public class ClientRestController {

    private final ClientService clientService;

    public ClientRestController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/api/client")
    public ClientDTO saveClient(@RequestBody ClientDTO clientDTO) {
        Client client = clientDTO.toModel();
        clientService.save(client);
        return clientDTO;
    }

}
