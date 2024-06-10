package com.rikishi.rikishi.service;

import com.itextpdf.text.*;
import com.rikishi.rikishi.generator.ReportGenerator;
import com.rikishi.rikishi.provider.ConfigProvider;
import com.rikishi.rikishi.provider.EmojiFlagProvider;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;

@Service
public class ReportService {
    private final UserService userService;
    private final FightService fightService;
    private final ConfigProvider configProvider;
    private final EmojiFlagProvider emojiFlagProvider;

    public ReportService(
        UserService userService,
        FightService fightService,
        ConfigProvider configProvider,
        EmojiFlagProvider emojiFlagProvider
    ) {
        this.userService = userService;
        this.fightService = fightService;
        this.configProvider = configProvider;
        this.emojiFlagProvider = emojiFlagProvider;
    }

    public void generate(String saveTo) throws DocumentException, FileNotFoundException {
        new ReportGenerator(userService, fightService, configProvider, emojiFlagProvider).generate(saveTo);
    }
}
