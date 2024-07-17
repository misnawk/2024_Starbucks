package com.example.starbucks.controller;


import com.example.starbucks.dto.ApiResponse;
import com.example.starbucks.repository.CoffeeRepository;
import com.example.starbucks.model.Coffee;
import com.example.starbucks.service.CoffeeService;
import com.example.starbucks.status.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


// http: request & response
// request: 메소드[get/post/put/delete/...]

// controller[경로 잡아주기]
// repository[db 데이터 가져오기]


@RestController
//localhost:8080/api/v1/coffee
@RequestMapping("api/v1/coffee")
public class CoffeeController {

    @Autowired
    private CoffeeRepository coffeeRepository;

    @Autowired
    CoffeeService coffeeService;



    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Coffee>>> getAllCoffees() {
        List<Coffee> coffeeList = coffeeService.getAllCoffees();
        ApiResponse<List<Coffee>> apiResponse = new ApiResponse<>(Status.SUCCESS, "성공", coffeeList);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<String>> addCoffee(@RequestBody Coffee coffee) {
        String result = coffeeService.addCoffee(coffee);
        if(result.equals("fail")){
            ApiResponse<String> apiResponse = new ApiResponse<>(Status.SUCCESS,"실패",null);
            return ResponseEntity.ok(apiResponse);
        }else{
            ApiResponse<String> apiResponse = new ApiResponse<>(Status.SUCCESS,"저장됨",null);
            return ResponseEntity.ok(apiResponse);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Coffee>>> getCoffeeByName(@RequestParam String name) {
        List<Coffee> coffeeList = coffeeService.getCoffeesByName(name);
        ApiResponse<List<Coffee>> apiResponse = new ApiResponse<>(Status.SUCCESS,"성공",coffeeList);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/coffeePrice")
    public List<Coffee> getCoffeeByPrice(@RequestParam int min, @RequestParam int max) {
        return coffeeRepository.findByPrice(min, max);
    }

}
