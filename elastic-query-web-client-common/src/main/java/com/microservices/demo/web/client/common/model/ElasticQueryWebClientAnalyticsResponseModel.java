package com.microservices.demo.web.client.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElasticQueryWebClientAnalyticsResponseModel
{
  private List<ElasticQueryWebClientResponseModel> elasticQueryServiceResponseModels;
  private Long wordCount;
}
