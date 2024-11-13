package com.example.crud1.controller;

import com.example.crud1.dto.Clientedto;
import com.example.crud1.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;
    @GetMapping("/clientes")
    public List<Clientedto> getCliente(){
        return clienteService.obtenerClientes();
    }
    @PostMapping("/crear")
    public ResponseEntity<?> crearCliente(@RequestBody Clientedto clientedto){
        try {
            clientedto = clienteService.registrarCliente(clientedto);
            return ResponseEntity.ok().body(clientedto);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Error en el proceso", HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/actualizar")
    public ResponseEntity<?> actualizarCliente(@RequestBody Clientedto clientedto){
        try{
            clientedto = clienteService.actualizarCliente(clientedto);
            return ResponseEntity.ok().body(clientedto);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Error en la actualización del cliente", HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/eliminar/{id}")
    public String deleteCliente(@PathVariable int id){
        return clienteService.eliminarCliente(id);
    }
}
