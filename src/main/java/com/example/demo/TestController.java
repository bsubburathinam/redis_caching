package com.example.demo;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class TestController {
    private final XyzSerivce xyzSerivce;

    @GetMapping("/student")
    public Student getStudent() {
        return xyzSerivce.getStudent();
    }

    @GetMapping("/profs")
    public List<Professor> getProfessors() {
        List<Professor> professors = xyzSerivce.getProfessors();
        return professors;
    }

    @GetMapping("/prof")
    public Professor getProfessor() {
        return xyzSerivce.getProfessor();
    }
}
