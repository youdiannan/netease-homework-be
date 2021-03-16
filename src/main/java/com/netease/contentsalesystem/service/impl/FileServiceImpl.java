package com.netease.contentsalesystem.service.impl;

import com.netease.contentsalesystem.service.IFileService;
import com.netease.contentsalesystem.vo.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.netease.contentsalesystem.constant.Const.DEFAULT_UPLOAD_PATH;

@Service("fileService")
public class FileServiceImpl implements IFileService {

    // 用来获取文件上传路径配置
    @Autowired
    private Environment environment;

    @Override
    public CommonResponse<Map<String, String>> upload(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
        // 先给默认的
        String path = environment.getProperty(DEFAULT_UPLOAD_PATH);
        String uploadFileName = UUID.randomUUID().toString().replace("-", "")+"."+fileExtensionName;

        File fileDir = new File(path);
        if(!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }

        File targetFile = new File(path, uploadFileName);
        try {
            file.transferTo(targetFile);
        } catch (IOException e) {
            return null;
        }
        Map<String, String> res = new HashMap<>();
        res.put("url", "/img/" + targetFile.getName());
        return new CommonResponse<>(res);
    }
}
