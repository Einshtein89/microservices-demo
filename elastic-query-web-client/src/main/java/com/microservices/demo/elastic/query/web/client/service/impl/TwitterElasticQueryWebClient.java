package com.microservices.demo.elastic.query.web.client.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.microservices.demo.config.ElasticQueryWebClientConfigData;
import com.microservices.demo.elastic.query.web.client.model.ElasticQueryWebClientRequestModel;
import com.microservices.demo.elastic.query.web.client.model.ElasticQueryWebClientResponseModel;
import com.microservices.demo.elastic.query.web.client.service.ElasticQueryWebClient;

@Service
public class TwitterElasticQueryWebClient implements ElasticQueryWebClient
{
  private static final Logger LOG = LoggerFactory.getLogger(TwitterElasticQueryWebClient.class);

  private final WebClient.Builder webClientBuilder;
  private final ElasticQueryWebClientConfigData elasticQueryWebClientConfigData;

  public TwitterElasticQueryWebClient(
      WebClient.Builder webClientBuilder,
      ElasticQueryWebClientConfigData elasticQueryWebClientConfigData)
  {
    this.webClientBuilder = webClientBuilder;
    this.elasticQueryWebClientConfigData = elasticQueryWebClientConfigData;
  }

  @Override
  public List<ElasticQueryWebClientResponseModel> queryByText(ElasticQueryWebClientRequestModel requestModel)
  {
    return List.of();
  }
}