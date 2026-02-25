package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController 
@RequestMapping("/api")
public class ChatApiController {

    @Autowired
    private CanalRepository canalRepository;

    @GetMapping("/canales")
    public List<String> obtenerListaCanales() {
        List<Canal> canales = canalRepository.findAll();
        List<String> infoCanales = new ArrayList<>();
        
        for (Canal canal : canales) {
            infoCanales.add("Sala: " + canal.getNombre() + " | Creada por: " + canal.getCreador().getNombre());
        }
        
        return infoCanales;
    }
}