package com.rikishi.rikishi.controller;

import com.rikishi.rikishi.model.WeightClass;
import com.rikishi.rikishi.model.entity.WeightCategories;
import com.rikishi.rikishi.provider.ResConfigProvider;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeightCategoriesController {
    private final ResConfigProvider resConfigProvider;

    WeightCategoriesController(ResConfigProvider rcp) {
        this.resConfigProvider = rcp;
    }

    @GetMapping("/weightCategories")
    public WeightCategories getWeightCategories() {
        return new WeightCategories(
            resConfigProvider.getWeightClasses().stream()
                .map(WeightClass::name).toList()
        );
    }
}
