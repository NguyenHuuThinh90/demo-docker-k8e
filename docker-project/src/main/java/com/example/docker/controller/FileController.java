package com.example.docker.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
public class FileController {

    @Resource
    private RedisTemplate redisTemplate;

    @GetMapping
    public ResponseEntity index() throws IOException {
        Files.write(Paths.get("./file/test.txt"), "Hello Demo".getBytes());
        System.out.println("READED");
        return ResponseEntity.ok().body("Thinh Docker");
    }


    @GetMapping("/redis")
    public ResponseEntity redis() {
        if (!redisTemplate.hasKey("t")) {
            redisTemplate.opsForValue().set("t", "Thinh");
        }
        return ResponseEntity.ok().body(redisTemplate.opsForValue().get("t"));
    }
}






