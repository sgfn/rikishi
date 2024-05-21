package com.rikishi.rikishi.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rikishi.rikishi.model.WeightClass;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * ConfigProvider that loads data from the resources.
 * Data was taken from <a href="http://www.ifs-sumo.org/ifs-weight-class.html">ifs-sumo.org</a>
 */
@Component
public class ResConfigProvider implements ConfigProvider {
    private final Config config;

    public ResConfigProvider(ObjectMapper mapper) throws IOException {
        config = mapper.readValue(getClass().getResource("/config.json"), Config.class);
    }

    @Override
    public List<WeightClass> getWeightClasses() {
        return config.weightClasses;
    }

    @Override
    public Optional<WeightClass> getWeightClassById(long id) {
        return config.weightClasses.stream()
            .filter(weightClass -> weightClass.id() == id)
            .findFirst();
    }

    @Override
    public Optional<WeightClass> getWeightClassByName(String name) {
        return config.weightClasses.stream()
            .filter(weightClass -> weightClass.name().equals(name))
            .findFirst();
    }

    private record Config(
        List<WeightClass> weightClasses
    ) {}
}
