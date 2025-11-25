package com.ljh.utils;

import com.ljh.controller.Result;
import com.ljh.controller.Status;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class imageUtil {
    public static String upload(MultipartFile file) throws IOException {
        //获取上传文件名
        String originalFilename = file.getOriginalFilename();

        //判断文件是否为空
        if (originalFilename != null && !originalFilename.isEmpty()) {
            //取新文件名
            String newOriginalFilename = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFileName = UUID.randomUUID().toString() + "." + newOriginalFilename;

            String property = System.getProperty("user.dir");
            //创建目录
            java.io.File newFile = new File(property + "/src/main/resources/static/images/" + newFileName);


            //以新文件名保存文件
            file.transferTo(newFile);

            String saveFile = "http://localhost/images/" + newFileName;
            return saveFile;
        }else {
            return null;
        }
    }
}
