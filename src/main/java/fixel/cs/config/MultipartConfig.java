package fixel.cs.config;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import org.springframework.core.io.FileSystemResource;

import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Configuration
public class MultipartConfig {

    @Value("${FileUploadApp.file.defaultPath}")
    public String defaultPath = "";

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {

        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setResolveLazily(true);   // 파일 첨부 액세스 시점에 다중 파트 요청을 느리게 할지를 결정함
        multipartResolver.setMaxUploadSize(1024 * 1024 * 10);   // 파일 최대 크기 설정
        multipartResolver.setDefaultEncoding(StandardCharsets.UTF_8.displayName()); // UTF-8로 파일 인코딩

        try {
            log.info("path: " + defaultPath);
            multipartResolver.setUploadTempDir(new FileSystemResource(defaultPath));
        } catch (IOException e) {
            log.error("init error", e);
        }

        return multipartResolver;

    }


}
