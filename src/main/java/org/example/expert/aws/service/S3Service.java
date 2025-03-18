package org.example.expert.aws.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3Service {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public String uploadFile(MultipartFile file, String fileName) throws IOException {

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        amazonS3.putObject(bucketName, fileName, file.getInputStream(), metadata);

        return getImageUrl(fileName);
    }

    public void deleteFile(String fileName){
        amazonS3.deleteObject(bucketName, fileName);
    }

    public String getImageUrl(String fileName){
        return amazonS3.getUrl(bucketName, fileName).toString();
    }

    public String createFileName(MultipartFile multipartFile){
        int index = multipartFile.getOriginalFilename().lastIndexOf(".");
        return UUID.randomUUID().toString() + multipartFile.getOriginalFilename().substring(index);
    }
}
