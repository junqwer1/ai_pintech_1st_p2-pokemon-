package org.koreait.global.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties(prefix = "file.upload") //범주안에서 설정파일을 쓰려고 사용
public class FileProperties {
    private String path;
    private String url;
}
