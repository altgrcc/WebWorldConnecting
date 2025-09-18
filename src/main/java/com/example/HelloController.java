package com.example;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hello") 
public class HelloController {

    @GetMapping("/get")
    public String helloGet() {
        return "Hello, World!";
    }

    @PostMapping
    public String helloPost() {
        return "Created item";
    }

    @PutMapping("/{id}")
    public String helloPut() {
        return "Updated item!";
    }

    @DeleteMapping("/{id}")
    public String helloDelete(@PathVariable String id) {
        return "Deleted item with id: " + id;
    }
}
