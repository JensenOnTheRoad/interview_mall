package demo.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

@Configuration
public class GsonConfig {

  @Bean
  public Gson gson() {
    return new GsonBuilder()
        .registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTime())
        .registerTypeAdapter(LocalDate.class, new GsonLocalDate())
        .registerTypeAdapter(Long.class, new GsonLong())
        .create();
  }

  @Bean
  public HttpMessageConverters customConverters() {
    Collection<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
    GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
    gsonHttpMessageConverter.setGson(gson());
    gsonHttpMessageConverter.setDefaultCharset(StandardCharsets.UTF_8);
    messageConverters.add(gsonHttpMessageConverter);
    messageConverters.add(responseBodyStringConverter());
    return new HttpMessageConverters(true, messageConverters);
  }

  @Bean
  public HttpMessageConverter<String> responseBodyStringConverter() {
    return new StringHttpMessageConverter(StandardCharsets.UTF_8);
  }
}
