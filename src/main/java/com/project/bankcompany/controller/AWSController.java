package com.project.bankcompany.controller;

import com.project.bankcompany.service.impl.AWSS3ServiceImpl;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/aws")
public class AWSController {



    private final Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Autowired
    private AWSS3ServiceImpl awsS3Service;


    @Value("${aws.s3.bucket}")
    private String bucketName;
//    @RequestBody
//    @RequestMapping(value = "/picture", method = RequestMethod.POST, consumes = {"multipart/from-data"})
//    public Map<String,String>uploadPicture(@RequestParam(value = "pic")MultipartFile picture){
//        Map<String, String> result = new HashMap<>(1);
//        try{
//            Image image = imageService.saveFakeImage(picture);
//            result.put("s3_url",image.getUrl());
//        }catch(ServiceException e){
//            logger.error("error on saving record", e);
//        }
//        return result;
//    }

    @RequestMapping(value = "/picture", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public void uploadMultiPartFile(@RequestParam(value = "FileName") MultipartFile fileName){
        try{
            String file = awsS3Service.uploadMultipartFile(bucketName,fileName);

        } catch(ServiceException|IOException e) {
            logger.error("=======error when uploading multipart file with message={}", e.getMessage());
        }
    }

    @RequestMapping(value = "/pic", method = RequestMethod.POST)
    public void uploadPicture(){
        awsS3Service.uploadObject(bucketName,"Group_Work.ipynb"
                ,"/Users/cliff/Desktop/Group_Work.ipynb");
    }

    @RequestMapping(value = "/presignedurl", method = RequestMethod.GET)
    public String generatePresignedURL(@RequestParam(value = "filename") String key){
        String presignedUrl = awsS3Service.generatePresignedURL(bucketName, key,
                HttpMethod.GET.toString());
        return presignedUrl;
    }


}
