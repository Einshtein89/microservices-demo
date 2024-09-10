package com.microservices.demo.elastic.query.client.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.microservices.demo.common.util.CollectionsUtil;
import com.microservices.demo.elastic.model.index.impl.TwitterIndexModel;
import com.microservices.demo.elastic.query.client.exception.ElasticQueryClientException;
import com.microservices.demo.elastic.query.client.repository.TwitterElasticSearchQueryRepository;
import com.microservices.demo.elastic.query.client.service.ElasticQueryClient;

@Service
@Primary
public class TwitterElasticSearchRepositoryQueryClient implements ElasticQueryClient<TwitterIndexModel>
{
  private static final Logger LOG = LoggerFactory.getLogger(TwitterElasticSearchRepositoryQueryClient.class);

  private final TwitterElasticSearchQueryRepository twitterElasticSearchQueryRepository;

  public TwitterElasticSearchRepositoryQueryClient(TwitterElasticSearchQueryRepository twitterElasticSearchQueryRepository)
  {
    this.twitterElasticSearchQueryRepository = twitterElasticSearchQueryRepository;
  }

  @Override
  public TwitterIndexModel getIndexModelById(String id)
  {
    final var result = twitterElasticSearchQueryRepository.findById(id);
    LOG.info("Retrieved index model with id {}",
        result.orElseThrow(() -> new ElasticQueryClientException("Could not find index model with id " + id)).getId());

    return result.get();
  }

  @Override
  public List<TwitterIndexModel> getIndexModelByText(String text)
  {
    final var result = twitterElasticSearchQueryRepository.findByText(text);
    LOG.info("{} of document with text {} retrieved successfully", result.size(), text);

    return result;
  }

  @Override
  public List<TwitterIndexModel> getAllIndexModels()
  {
    final var result = CollectionsUtil.getInstance()
        .getListFromIterable(twitterElasticSearchQueryRepository.findAll());
    LOG.info("{} number of documents retrieved successfully", result.size());

    return result;
  }
}
