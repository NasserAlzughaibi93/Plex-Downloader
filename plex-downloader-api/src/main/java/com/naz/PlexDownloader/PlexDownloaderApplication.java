package com.naz.PlexDownloader;

import com.naz.PlexDownloader.util.BuildVersion;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PlexDownloaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlexDownloaderApplication.class, args);
		//BuildVersion.getOSInfo();
	}

}
