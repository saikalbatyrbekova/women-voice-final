package com.example.women_voice.mapper.impl;

import com.example.women_voice.mapper.FileMapper;
import com.example.women_voice.model.domain.MyFile;
import org.springframework.stereotype.Component;

@Component
public class FileMapperImpl implements FileMapper {
    @Override
    public MyFile toFile(String fileName, String filePath) {
        MyFile file = new MyFile();
        file.setFileName(fileName);
        file.setPath(filePath);
        return file;
    }
}
