package com.elk.log.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.elk.log.web.LogDto;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LogUtil {
	
	public static final String PATH = "C://Users//hanssak//Documents//WD//ELK//logs";
	public static final String FILE_NAME = "logFile.log";
	
	public static void printLog(LogDto log) {
		PrintWriter out = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Include.NON_EMPTY);
			String outStr = mapper.writeValueAsString(log);
			
			checkDirectoryExist(PATH);
			
			String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
			String[] arrFilePath = FILE_NAME.split("[.]");
			String filePath = PATH + "//" + arrFilePath[0] + "_" + date + "." + arrFilePath[1];

			FileWriter fw = new FileWriter(filePath, true); // true가 append 모드
			BufferedWriter bw = new BufferedWriter(fw);
			out = new PrintWriter(bw);

			out.println(outStr); // 파일에 출력
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}
	
	public static void checkDirectoryExist(String uploadPath) throws IOException { 
		Path filePath = Paths.get(uploadPath);
		
		if(!Files.isDirectory(filePath)){
			Files.createDirectories(filePath);
		}
	}
	
	public static void main(String[] args) {
		LogDto logDto = new LogDto();
		Map<String, Object> map = new HashMap<>();
		map.put("type", "hello");
		map.put("value", "iiii");
		List<Map<String, Object>> list = new ArrayList<>();
		
		list.add(map);
		
		logDto.setParamList(list);
		
		printLog(logDto);
	}
	
}
