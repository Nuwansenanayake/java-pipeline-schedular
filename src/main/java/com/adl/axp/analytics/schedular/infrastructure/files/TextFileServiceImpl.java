package com.adl.axp.analytics.schedular.infrastructure.repository;

import com.adl.axp.analytics.schedular.core.ports.TextFileService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class TextFileServiceImpl implements  TextFileService {
    @Override
    public  String readfile(String file){
        String text = "";
        try {
            text = new String( Files.readAllBytes(Paths.get(file)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
}
