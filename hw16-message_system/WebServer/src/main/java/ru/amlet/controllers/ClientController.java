package ru.amlet.controllers;

import java.util.List;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.amlet.dao.Client;
import ru.amlet.dto.ClientDTO;
import ru.amlet.dto.ClientsListDto;
import ru.amlet.services.ClientService;

@Controller
public class ClientController {

    private final SimpMessagingTemplate template;
    private final ClientService clientService;

    public ClientController(SimpMessagingTemplate template, ClientService clientService) {
        this.template = template;
        this.clientService = clientService;
    }

    @MessageMapping("client")
    public void saveClient(ClientDTO clientDto) {
        clientService.save(clientDto, this::postSavedClient);
    }

    public void postSavedClient(ClientDTO clientDto) {
        template.convertAndSend("/topic/client", clientDto);
    }

    @MessageMapping("clients")
    public void getAllClients() {
        clientService.findAll(this::postAllClients);
    }

    public void postAllClients(ClientsListDto clientsListDto) {
        template.convertAndSend("/topic/clients", clientsListDto.getAll());
    }

}
