package com.example.starbucks.service;

import com.example.starbucks.model.Coffee;

import java.util.List;

public interface CoffeeService {
    List<Coffee> getAllCoffees();
    List<Coffee> getCoffeesByName(String name);
    List<Coffee> getCoffeeByPrice(int min, int max);
    String addCoffee(Coffee coffee);
}
