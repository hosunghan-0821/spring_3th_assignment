package com.example.spring_3th_assignment.Controller;


import com.example.spring_3th_assignment.Controller.response.ImageResponseDto;
import com.example.spring_3th_assignment.service.S3UploaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class S3Controller {
    private final S3UploaderService s3Uploader;

    //다중이미지 받을 떄
    //List<MultipartFile> files

    @PostMapping("/api/image/image")
    public ImageResponseDto imageUpload(@RequestParam("image") MultipartFile multipartFile){

        try{
           return new ImageResponseDto(s3Uploader.uploadFiles(multipartFile,"static")) ;
        }catch (Exception e){
            e.printStackTrace();
            return new ImageResponseDto();
        }

    }
}
