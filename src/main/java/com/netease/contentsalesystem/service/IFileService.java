package com.netease.contentsalesystem.service;

import com.netease.contentsalesystem.vo.CommonResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface IFileService {

    CommonResponse<Map<String, String>> upload(MultipartFile file);
}
