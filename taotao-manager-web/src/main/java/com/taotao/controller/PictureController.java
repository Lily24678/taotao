package com.taotao.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
/**
 * @Description: 图片上传Controller
 * @author nq  nianqiang@itcast.cn
 */
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.utils.FastDFSClient;
import com.taotao.utils.JsonUtils;
@Controller
public class PictureController {
	
	@Value("${UPLOAD_FILE_SERVER_URL}")
	private String UPLOAD_FILE_SERVER_URL;
	
	@RequestMapping(value="/pic/upload",produces=MediaType.TEXT_PLAIN_VALUE+";charset=utf-8")
	@ResponseBody
	public String uploadPicture(MultipartFile uploadFile){
		String conf = "classpath:conf/fast_dfs.conf";
		try {
			// 加载配置文件
			FastDFSClient client = new FastDFSClient(conf);
			String filename = uploadFile.getOriginalFilename();
			String extName = filename.substring(filename.lastIndexOf(".")+1);
			
			String path = client.uploadFile(uploadFile.getBytes(), extName);
			// 文件的全路径
			String url = UPLOAD_FILE_SERVER_URL + path;
			
			Map<String,Object> resultMap = new HashMap<>();
			resultMap.put("error", 0);
			resultMap.put("url", url);
			
			return JsonUtils.objectToJson(resultMap);
			
		} catch (Exception e) {
			e.printStackTrace();
			Map<String,Object> resultMap = new HashMap<>();
			resultMap.put("error", 1);
			resultMap.put("message", "文件上传失败");
			return JsonUtils.objectToJson(resultMap);
		}
	}
}
