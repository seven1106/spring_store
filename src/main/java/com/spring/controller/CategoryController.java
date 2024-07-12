package com.spring.controller;

import com.spring.model.Category;
import com.spring.model.User;
import com.spring.service.CategoryService;
import com.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/categories")
public class CategoryController {
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category req,
                                                   @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByJwtToken(token);
        Category category = categoryService.createCategory(req.getName(), user.getId());
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }
    @GetMapping("/categories/{restaurantId}")
    public ResponseEntity<List<Category>> findCategoriesByRestaurantId(
                                                                       @RequestHeader("Authorization") String token) throws Exception {
        User user = userService.findUserByJwtToken(token);
        List<Category> categories = categoryService.findCategoriesByRestaurantId(user.getId());
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
