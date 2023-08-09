package com.project.bankcompany.controller;


import com.project.bankcompany.dto.ClientDto;
import com.project.bankcompany.exception.ExceptionResponse;
import com.project.bankcompany.exception.ItemNotFoundException;
import com.project.bankcompany.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/project_bank")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/clients/signup")
    public ResponseEntity<ClientDto> signUp(@RequestBody ClientDto clientDto) {
        ClientDto savedClientDto = clientService.signUp(clientDto, clientDto.getManager().getId());
        if (savedClientDto != null) {
            return new ResponseEntity<>(savedClientDto, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/clients/signin")
    public ResponseEntity<ClientDto> signIn(@RequestParam String loginName, @RequestParam String password) {
        ClientDto signedInClientDto = clientService.signIn(loginName, password);
        if (signedInClientDto != null) {
            return new ResponseEntity<>(signedInClientDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/clients")
    public ResponseEntity<List<ClientDto>> findAllClients() {
        List<ClientDto> clientDtoList = clientService.getClients();
        return ResponseEntity.ok(clientDtoList);
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<Object> findClientById(@PathVariable("id") Long clientId) {
        try {
            ClientDto clientDto = clientService.getClientById(clientId);
            return ResponseEntity.ok(clientDto);
        } catch (ItemNotFoundException e) {
            ExceptionResponse exceptionResponse = new ExceptionResponse("Client not found", LocalDateTime.now(), e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
        }
    }

    @PostMapping("/clients")
    public ResponseEntity<ClientDto> saveClient(@RequestBody ClientDto clientDto) {
        // Validate clientDto, perform any necessary processing, and then save
        ClientDto savedClientDto = clientService.save(clientDto,clientDto.getManager().getId());
        return ResponseEntity.created(URI.create("/project_bank/clients/" + savedClientDto.getId())).body(savedClientDto);
    }

    @PutMapping("/clients")
    public ResponseEntity<ClientDto> updateClient(@RequestBody ClientDto clientDto) {
        // Validate clientDto, perform any necessary processing, and then update
        ClientDto updatedClientDto = clientService.update(clientDto);
        return ResponseEntity.ok(updatedClientDto);
    }

    @DeleteMapping("/clients/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable("id") Long clientId) {
        // Delete the client by ID
        boolean deleteResult = clientService.deleteById(clientId);
        if (deleteResult) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).build();
        }
    }
}
