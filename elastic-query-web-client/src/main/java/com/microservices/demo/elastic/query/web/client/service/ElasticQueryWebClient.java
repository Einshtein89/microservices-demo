package com.microservices.demo.elastic.query.web.client.service;

import java.util.List;

import com.microservices.demo.web.client.common.model.ElasticQueryWebClientAnalyticsResponseModel;
import com.microservices.demo.web.client.common.model.ElasticQueryWebClientRequestModel;
import com.microservices.demo.web.client.common.model.ElasticQueryWebClientResponseModel;

public interface ElasticQueryWebClient
{
  ElasticQueryWebClientAnalyticsResponseModel queryByText(ElasticQueryWebClientRequestModel requestModel);
}
