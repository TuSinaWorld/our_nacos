package com.our_nacos.client.file.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/file")
public class FileController {
    @RequestMapping("/download/{fileName}")
    public ResponseEntity<Byte[]> download(HttpServletRequest request, @PathVariable String fileName){

    }
}
