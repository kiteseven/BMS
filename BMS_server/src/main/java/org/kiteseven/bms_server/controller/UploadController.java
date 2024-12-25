package org.kiteseven.bms_server.controller;

import lombok.extern.slf4j.Slf4j;
import org.kiteseven.bms_common.result.Result;
import org.kiteseven.bms_common.utils.AliOssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/upload")
public class UploadController {
    @Autowired
    AliOssUtil aliOssUtil;
    @PostMapping("/uploadImage")
    public Result<String> uploadComicImage(@RequestParam("file") MultipartFile file) throws IOException {
        log.info("上传图片");
        log.info(file.toString());
        String oriName= file.getOriginalFilename();
        String objName = "BMS-" + oriName;
        String imgUrl=aliOssUtil.upload(file.getBytes(),objName);
        return Result.success(imgUrl);
    }
}
