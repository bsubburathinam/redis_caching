package com.example.demo;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XyzServiceImpl implements XyzSerivce {
    @Override
    @Cacheable(value = "student")
    public Student getStudent() {
        System.out.println("XyzServiceImpl::getStudent invoked.");
        return new Student("test first name", "test last name");
    }



    @Override
    @Cacheable(value = "profs", cacheManager = "professorListCacheManager")
    public List<Professor> getProfessors() {
        System.out.println("XyzServiceImpl::getProfessors invoked.");
        return
            List.of(
                new Professor("Prof 1 FN", "Prof 1 LN", "Prof 1 desig"),
                new Professor("Prof 2 FN", "Prof 2 LN", "Prof 2 desig")
            );
    }

    @Override
    @Cacheable(value = "prof")
    public Professor getProfessor() {
        System.out.println("XyzServiceImpl::getProfessor invoked.");
        return new Professor("Prof 1 FN", "Prof 1 LN", "Prof 1 desig");
    }
}
