package com.rikishi.rikishi.provider;

import com.rikishi.rikishi.model.WeightClass;

import java.util.List;
import java.util.Optional;

public interface ConfigProvider {
    List<WeightClass> getWeightClasses();
    Optional<WeightClass> getWeightClassById(long id);
}
