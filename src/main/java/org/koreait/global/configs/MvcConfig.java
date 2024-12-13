package org.koreait.global.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableJpaAuditing /*자동 감지*/
@EnableScheduling /*scheduled 같은 애너테이션 자동 실행하기 위해서*/
@EnableRedisHttpSession /*레디스가 세션에 저장할 수 있게 해주는?*/
public class MvcConfig implements WebMvcConfigurer/*스프링 기본 설정*/ {
    /**
     * 정적 경로 설정, CSS, JS, 이미지
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/"); /*클래스 파일이 인식할 수 있는 경로*/
    }

    /**
    * PATCH, PUT, DELETE 등등
    * Patch 메서드로 요청을 보내는 경우
    * <form Method='POST'
    *   <input type='hidden' name='_method' value='patch'>
    * */
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }
}
