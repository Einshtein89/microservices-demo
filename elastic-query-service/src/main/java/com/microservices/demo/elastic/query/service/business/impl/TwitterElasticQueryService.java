package com.microservices.demo.elastic.query.service.business.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.microservices.demo.elastic.model.index.impl.TwitterIndexModel;
import com.microservices.demo.elastic.query.client.service.ElasticQueryClient;
import com.microservices.demo.elastic.query.service.business.ElasticQueryService;
import com.microservices.demo.elastic.query.service.common.model.ElasticQueryServiceResponseModel;
import com.microservices.demo.elastic.query.service.model.assembler.ElasticQueryServiceResponseModelAssembler;

@Service
public class TwitterElasticQueryService implements ElasticQueryService
{
  private static final Logger LOG = LoggerFactory.getLogger(TwitterElasticQueryService.class);

  private final ElasticQueryServiceResponseModelAssembler elasticQueryServiceResponseModelAssembler;
  private final ElasticQueryClient<TwitterIndexModel> elasticQueryClient;

  public TwitterElasticQueryService(
      ElasticQueryServiceResponseModelAssembler elasticQueryServiceResponseModelAssembler,
      ElasticQueryClient<TwitterIndexModel> elasticQueryClient)
  {
    this.elasticQueryServiceResponseModelAssembler = elasticQueryServiceResponseModelAssembler;
    this.elasticQueryClient = elasticQueryClient;
  }

  @Override
  public ElasticQueryServiceResponseModel getDocumentById(String documentId)
  {
    LOG.info("Querying elasticsearch for document by id: {}", documentId);

    return elasticQueryServiceResponseModelAssembler.toModel(elasticQueryClient.getIndexModelById(documentId));
  }

  @Override
  public List<ElasticQueryServiceResponseModel> getDocumentByText(String text)
  {
    LOG.info("Querying elasticsearch for document by text: {}", text);

    return elasticQueryServiceResponseModelAssembler.toModels(elasticQueryClient.getIndexModelByText(text));
  }

  @Override
  public List<ElasticQueryServiceResponseModel> getAllDocuments()
  {
    LOG.info("Querying elasticsearch for all documents");

    return elasticQueryServiceResponseModelAssembler.toModels(elasticQueryClient.getAllIndexModels());
  }
}
