package com.microservices.demo.elastic.query.web.client.service;

import java.util.List;

import com.microservices.demo.web.client.common.model.ElasticQueryWebClientRequestModel;
import com.microservices.demo.web.client.common.model.ElasticQueryWebClientResponseModel;

public interface ElasticQueryWebClient
{
  List<ElasticQueryWebClientResponseModel> queryByText(ElasticQueryWebClientRequestModel requestModel);
}
