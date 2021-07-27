package com.storactive.stg.config;

import com.storactive.stg.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.datatables.repository.DataTablesRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

@Configuration
@EnableJpaRepositories(
        repositoryFactoryBeanClass = DataTablesRepositoryFactoryBean.class,
        basePackages = "com.storactive.stg.repository"
)
@RequiredArgsConstructor
public class MvcConfigurer implements WebMvcConfigurer {

    @Value("${spring.max-upload-size}")
    private long MAX_UPLOAD_SIZE;
    final AlertService alertSer;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
        registry.addInterceptor(alertReadInterceptor())
                .addPathPatterns("/internships/{id:[1-9][0-9]*}");
        registry.addInterceptor(alertInterceptor())
                .excludePathPatterns("/login*", "/logout*");
    }


    @Bean
    public AlertInterceptor alertInterceptor() {
        return new AlertInterceptor(alertSer);
    }

    @Bean
    public AlertReadInterceptor alertReadInterceptor() {
        return new AlertReadInterceptor(alertSer);
    }

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver slr = new CookieLocaleResolver();
        slr.setDefaultLocale(Locale.US);
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(MAX_UPLOAD_SIZE);
        return multipartResolver;
    }


}
