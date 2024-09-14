package com.microservices.demo.elastic.query.service.common.transformer;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.microservices.demo.elastic.model.index.impl.TwitterIndexModel;
import com.microservices.demo.elastic.query.service.common.model.ElasticQueryServiceResponseModel;

@Component
public class ElasticToResponseModelTransformer
{
  public ElasticQueryServiceResponseModel toModel(TwitterIndexModel twitterIndexModel)
  {
    return ElasticQueryServiceResponseModel
        .builder()
        .id(twitterIndexModel.getId())
        .userId(twitterIndexModel.getUserId())
        .text(twitterIndexModel.getText())
        .createdAt(twitterIndexModel.getCreatedAt())
        .build();
  }

  public List<ElasticQueryServiceResponseModel> toModels(List<TwitterIndexModel> twitterIndexModels)
  {
    return twitterIndexModels.stream()
        .map(this::toModel)
        .collect(Collectors.toList());
  }
}
