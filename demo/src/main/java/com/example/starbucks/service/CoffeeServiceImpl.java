package com.example.starbucks.service;

import com.example.starbucks.model.Coffee;
import com.example.starbucks.repository.CoffeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoffeeServiceImpl implements CoffeeService {

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Override
    public List<Coffee> getAllCoffees() {
        return coffeeRepository.findAll();
    }

    @Override
    public List<Coffee> getCoffeesByName(String name) {
        return coffeeRepository.findByName(name);
    }

    @Override
    public List<Coffee> getCoffeeByPrice(int min, int max) {
        return coffeeRepository.findByPrice(min, max);
    }

    @Override
    public String addCoffee(Coffee coffee) {
        try {
            coffeeRepository.save(coffee);
            return "success";
        } catch (Exception e) {
            return "fail";
        }
    }
}
