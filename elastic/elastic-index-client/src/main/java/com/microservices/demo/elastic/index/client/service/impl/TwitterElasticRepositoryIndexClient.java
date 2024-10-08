package com.microservices.demo.elastic.index.client.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.microservices.demo.elastic.index.client.repository.TwitterElasticSearchIndexRepository;
import com.microservices.demo.elastic.index.client.service.ElasticIndexClient;
import com.microservices.demo.elastic.model.index.impl.TwitterIndexModel;

@Service
@ConditionalOnProperty(name = "elastic-config.is-repository", havingValue = "true", matchIfMissing = true)
public class TwitterElasticRepositoryIndexClient implements ElasticIndexClient<TwitterIndexModel>
{
  private static final Logger LOG = LoggerFactory.getLogger(TwitterElasticRepositoryIndexClient.class);
  private final TwitterElasticSearchIndexRepository twitterElasticSearchIndexRepository;

  public TwitterElasticRepositoryIndexClient(TwitterElasticSearchIndexRepository twitterElasticSearchIndexRepository)
  {
    this.twitterElasticSearchIndexRepository = twitterElasticSearchIndexRepository;
  }

  @Override
  public List<String> save(List<TwitterIndexModel> documents)
  {
    LOG.info("Using Repository Client implementation");
    final var twitterIndexModels = (List<TwitterIndexModel>) twitterElasticSearchIndexRepository.saveAll(documents);
    final var ids = twitterIndexModels.stream()
        .map(TwitterIndexModel::getId)
        .collect(Collectors.toList());
    LOG.info("Documents indexed successfully with type: {} and ids: {}", TwitterIndexModel.class.getName(),
        ids);

    return ids;
  }
}
