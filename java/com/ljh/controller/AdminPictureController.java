package com.ljh.controller;

import com.ljh.service.AdminPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/adminPicture")
public class AdminPictureController {
    @Autowired
    private AdminPictureService adminPictureService;

    @RequestMapping("/insert")
    @ResponseBody
    public Result insert(String imageUrl){
        if (adminPictureService.insert(imageUrl)){
            return new Result(null,Status.PICTURE_SUCCESS,"修改成功");
        }else {
            return new Result(null,Status.PICTURE_ERROR,"修改失败");
        }
    }

    @RequestMapping("/deleteAll")
    @ResponseBody
    public boolean deleteAll(){
        return adminPictureService.deleteAll();
    }

    @RequestMapping("/selectAll")
    @ResponseBody
    public List<String> selectAll(){
        return adminPictureService.selectAll();
    }
}
