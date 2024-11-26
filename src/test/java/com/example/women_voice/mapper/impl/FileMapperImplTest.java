package com.example.women_voice.mapper.impl;

import com.example.women_voice.model.domain.MyFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileMapperImplTest {

    private FileMapperImpl fileMapper;

    @BeforeEach
    void setUp() {
        fileMapper = new FileMapperImpl();
    }

    @Test
    void toFile_ShouldMapFileNameAndPathToMyFile() {
        // Arrange
        String fileName = "test-file.jpg";
        String filePath = "https://example.com/files/test-file.jpg";

        // Act
        MyFile result = fileMapper.toFile(fileName, filePath);

        // Assert
        assertNotNull(result, "Resulting MyFile object should not be null");
        assertEquals(fileName, result.getFileName(), "File name should match the input");
        assertEquals(filePath, result.getPath(), "File path should match the input");
    }
}
