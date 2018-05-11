package com.coderushers.filesystem.controller;

import com.coderushers.filesystem.component.FastDFSClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@CrossOrigin
@RequestMapping(value = "file")
public class FastDFSController {
    @Autowired
    FastDFSClient fastDFSClient;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Object upload(@RequestParam MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Object imgUrl = fastDFSClient.uploadFile(file);
        return imgUrl;
    }
}
