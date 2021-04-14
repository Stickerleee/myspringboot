package com.msb.jsonboot.common;

import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.StringUtils;

public class JsonBootBanner implements Banner {
	
    /**
     * 默认的启动文件名称
     */
	
	private static final String DEFAULT_BANNER_NAME = "jsonbootBanner.txt";
	
	@Override
	public void printBanner(String bannerName, PrintStream printStream) {
		// TODO Auto-generated method stub
		if(StringUtils.isBlank(bannerName)) {
			bannerName = DEFAULT_BANNER_NAME;
		}
			
		URL url = Thread.currentThread().getContextClassLoader().getResource(bannerName);
		if (url == null) {
			return;
		}
		try {
			Path path = Paths.get(url.toURI());
			Files.lines(path).forEach(printStream::println);
			printStream.println();
		} catch(URISyntaxException | IOException e) {
			printStream.printf("banner文件加载错误 banner: %s, error: %s",bannerName, e);
		}
		

	}

}
