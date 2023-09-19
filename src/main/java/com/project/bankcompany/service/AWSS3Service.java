package com.project.bankcompany.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface AWSS3Service {

    void setAmazonS3(AmazonS3 amazonS3);

    boolean isBucketExist(String bucketName);

    boolean isObjectExist(String bucketName, String objectKey);

    Bucket createBucket(String bucketName);

    List<Bucket> getBucketList();

    void deleteBucket(String bucketName);

    String uploadFile(MultipartFile file) throws IOException;

    String uploadMultipartFile(String bucketName, MultipartFile multipartFile) throws IOException;

    void uploadObject(String bucketName, String key, String fullFilepath);
    ObjectListing listObjects(String bucketName);

    List<String> findObjectKeyList(String bucketName);

    File downloadObject(String bucketName, String objectKey, String destinationFullPath)  throws IOException;

    void copyObject(String oriBucketName, String oriObjectKey, String destBucketName, String destObjectKey);

    void copyObjectUsingCopyObjectRequest(
            String oriBucketName,
            String oriObjectKey,
            String destBucketName,
            String destObjectKey);

    void deleteObject(String bucketName, String objectKey);

    void deleteMultipleObjects(String bucketName, String[] objectKeyArray);

    String generatePresignedURLForUploading(String bucketName, String objectKey);

    String generatePresignedURLForDownloading(String objectKey);

    String generatePresignedURLForDownloading(String bucketName, String objectKey);

    String generatePresignedURL(String bucketName, String objectKey, String httpMethodString);

}
