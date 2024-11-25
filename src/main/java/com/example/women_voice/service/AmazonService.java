package com.example.women_voice.service;

import com.example.women_voice.model.domain.MyFile;
import org.springframework.web.multipart.MultipartFile;

public interface AmazonService {
    MyFile uploadFile(MultipartFile file);
    void deleteFile(String filName);
}
