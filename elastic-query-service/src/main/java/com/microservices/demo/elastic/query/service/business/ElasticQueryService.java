package com.microservices.demo.elastic.query.service.business;

import java.util.List;

import com.microservices.demo.elastic.query.service.model.ElasticQueryServiceResponseModel;

public interface ElasticQueryService
{
  ElasticQueryServiceResponseModel getDocumentById(String documentId);

  List<ElasticQueryServiceResponseModel> getDocumentByText(String text);

  List<ElasticQueryServiceResponseModel> getAllDocuments();
}
