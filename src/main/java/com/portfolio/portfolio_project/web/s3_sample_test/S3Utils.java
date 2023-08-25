package com.portfolio.portfolio_project.web.s3_sample_test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.portfolio.portfolio_project.core.exception.Exception400;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class S3Utils {

    public static List<String> uploadFile(MultipartFile multipartFile, String keyword, String bucket,
            AmazonS3Client amazonS3Client) throws IOException {
        ObjectMetadata objectMetadata = S3Utils.makeObjectMetadata(multipartFile);

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = S3Utils.makeStoreFileName(originalFilename);
        String key = S3Utils.branchFolder(keyword, storeFileName);

        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(bucket, key, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new Exception400("S3 업로드 중 오류 발생" +  e.getMessage());
        }
        String storeFileUrl = amazonS3Client.getUrl(bucket, key).toString();
        List<String> nameAndUrl = new ArrayList<>();
        nameAndUrl.add(key);
        nameAndUrl.add(storeFileUrl);

        return nameAndUrl;
    }

    public static String makeStoreFileName(String originalFilename) {
        int index = originalFilename.lastIndexOf(".");
        String name = originalFilename.substring(0, index);
        String ext = originalFilename.substring(index + 1);
        String storeFileName = name + "_" + UUID.randomUUID() + "." + ext;

        return storeFileName;
    };

    public static ObjectMetadata makeObjectMetadata(MultipartFile multipartFile) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(multipartFile.getContentType());
        objectMetadata.setContentLength(multipartFile.getSize());

        return objectMetadata;
    };

    public static String branchFolder(String keyword, String storeFileName) {
        String key;
        switch (keyword) {
            case "main_introduce":
                key = "main_introduce/" + storeFileName;
                break;
            case "my_project":
                key = "my_project/" + storeFileName;
                break;
            case "my_project_performance":
                key = "my_project_performance/" + storeFileName;
                break;
            case "my_blog":
                key = "my_blog/" + storeFileName;
                break;
            default:
                throw new IllegalArgumentException("Invalid keyword: " + keyword);
        }
        return key;
    }

    
}
