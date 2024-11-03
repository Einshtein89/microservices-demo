package com.microservices.demo.elastic.config;

import com.microservices.demo.config.ElasticConfigData;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.microservices.demo.elastic")
public class ElasticSearchConfig extends ElasticsearchConfiguration {

  private final ElasticConfigData elasticConfigData;

  public ElasticSearchConfig(ElasticConfigData configData) {
    this.elasticConfigData = configData;
  }

  @Override
  public ClientConfiguration clientConfiguration() {
    return ClientConfiguration.builder()
        .connectedTo(elasticConfigData.getConnectionUrl())
        .withConnectTimeout(elasticConfigData.getConnectionTimeout())
        .withSocketTimeout(elasticConfigData.getSocketTimeout())
        .build();
  }


}
