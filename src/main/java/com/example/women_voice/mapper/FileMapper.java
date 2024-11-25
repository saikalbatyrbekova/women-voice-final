package com.example.women_voice.mapper;

import com.example.women_voice.model.domain.MyFile;

public interface FileMapper {
    MyFile toFile(String fileName, String filePath);
}
