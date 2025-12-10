package com.atlashorticole.product_service.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.atlashorticole.product_service.services.CloudinaryService;

@WebMvcTest(CloudinaryController.class)
class CloudinaryControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockitoBean
        private CloudinaryService cloudinaryService;

        @Test
        void testHealthCheck() throws Exception {
                mockMvc.perform(get("/api/cloudinary/health"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.status").value("OK"));
        }

        @Test
        void testUploadImage_Success() throws Exception {
                // Mock du service
                when(cloudinaryService.uploadImage(any()))
                                .thenReturn("https://res.cloudinary.com/test/image/upload/test.jpg");

                MockMultipartFile imageFile = new MockMultipartFile(
                                "file",
                                "test.jpg",
                                "image/jpeg",
                                "fake image content".getBytes());

                mockMvc.perform(multipart("/api/cloudinary/upload/image")
                                .file(imageFile))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.fileType").value("image"))
                                .andExpect(jsonPath("$.message").exists());
        }

        @Test
        void testUploadPdf_Success() throws Exception {
                // Mock du service
                when(cloudinaryService.uploadPdf(any()))
                                .thenReturn("https://res.cloudinary.com/test/raw/upload/test.pdf");

                MockMultipartFile pdfFile = new MockMultipartFile(
                                "file",
                                "document.pdf",
                                "application/pdf",
                                "fake pdf content".getBytes());

                mockMvc.perform(multipart("/api/cloudinary/upload/pdf")
                                .file(pdfFile))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.fileType").value("pdf"))
                                .andExpect(jsonPath("$.message").exists());
        }

}