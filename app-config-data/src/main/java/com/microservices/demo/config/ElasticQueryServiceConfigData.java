package com.microservices.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "elastic-query-service")
public class ElasticQueryServiceConfigData {
  private String version;
  private String customAudience;
  private Long backPressureDelayMs;
  private WebClient webClient;
  private Query queryFromKafkaStateStore;

  @Data
  public static class WebClient {
    private Integer connectTimeoutMs;
    private Integer readTimeoutMs;
    private Integer writeTimeoutMs;
    private Integer maxInMemorySize;
    private String contentType;
    private String acceptType;
    private String queryType;
  }

  @Data
  public static class Query {
    private String method;
    private String accept;
    private String uri;
  }
}

