package com.example.women_voice.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.women_voice.exception.CustomException;
import com.example.women_voice.mapper.FileMapper;
import com.example.women_voice.model.domain.MyFile;
import com.example.women_voice.repository.FileRepository;
import com.example.women_voice.service.AmazonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AmazonServiceImpl implements AmazonService {
    private final FileRepository fileRepository;
    private final AmazonS3 amazonS3;
    private final FileMapper fileMapper;
    @Value("${application.bucket.name}")
    private String bucketName;

    @Override
    public MyFile uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new CustomException("Incorrect file", HttpStatus.BAD_REQUEST);
        }
        File convertedFile = convertMultipartFileToFile(file);
        String fileName = System.currentTimeMillis() + "_" + Objects.requireNonNull(file.getOriginalFilename()).replaceAll("\\s+", "_");;
        amazonS3.putObject(new PutObjectRequest(bucketName, fileName, convertedFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        boolean delete = convertedFile.delete();
        if (!delete) {
            throw new CustomException("Failed to delete file", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return fileRepository.save(fileMapper.toFile(fileName, amazonS3.getUrl(bucketName, fileName).toString()));
    }

    @Override
    public void deleteFile(String filName) {
        amazonS3.deleteObject(bucketName, filName);
    }

    private File convertMultipartFileToFile(MultipartFile file) {
        File convertFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(convertFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            throw new CustomException("Error while converting multipart file", HttpStatus.BAD_GATEWAY);
        }
        return convertFile;
    }
}
