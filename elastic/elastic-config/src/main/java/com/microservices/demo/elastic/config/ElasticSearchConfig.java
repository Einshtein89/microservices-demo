package com.microservices.demo.elastic.config;

import java.util.Objects;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.web.util.UriComponentsBuilder;

import com.microservices.demo.config.ElasticConfigData;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.microservices.demo.elastic")
public class ElasticSearchConfig extends AbstractElasticsearchConfiguration
{
  private final ElasticConfigData elasticConfigData;

  public ElasticSearchConfig(ElasticConfigData elasticConfigData)
  {
    this.elasticConfigData = elasticConfigData;
  }

  @Override
  @Bean
  public RestHighLevelClient elasticsearchClient()
  {
    final var serverUri = UriComponentsBuilder.fromHttpUrl(elasticConfigData.getConnectionUrl()).build();

    return new RestHighLevelClient(
        RestClient.builder(
                new HttpHost(Objects.requireNonNull(serverUri.getHost()), serverUri.getPort(), serverUri.getScheme())
            )
            .setRequestConfigCallback(requestConfigBuilder ->
                requestConfigBuilder
                    .setConnectTimeout(elasticConfigData.getConnectionTimeout())
                    .setSocketTimeout(elasticConfigData.getSocketTimeout())
            )
    );
  }

  @Bean
  public ElasticsearchOperations elasticsearchTemplate()
  {
    return new ElasticsearchRestTemplate(elasticsearchClient());
  }

}
