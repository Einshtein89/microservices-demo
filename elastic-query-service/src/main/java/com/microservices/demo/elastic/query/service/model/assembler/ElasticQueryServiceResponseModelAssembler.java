package com.microservices.demo.elastic.query.service.model.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.microservices.demo.elastic.model.index.impl.TwitterIndexModel;
import com.microservices.demo.elastic.query.service.api.ElasticDocumentController;
import com.microservices.demo.elastic.query.service.common.model.ElasticQueryServiceResponseModel;
import com.microservices.demo.elastic.query.service.common.transformer.ElasticToResponseModelTransformer;

@Component
public class ElasticQueryServiceResponseModelAssembler
    extends RepresentationModelAssemblerSupport<TwitterIndexModel, ElasticQueryServiceResponseModel>
{
  private final ElasticToResponseModelTransformer elasticToResponseModelTransformer;

  public ElasticQueryServiceResponseModelAssembler(ElasticToResponseModelTransformer elasticToResponseModelTransformer)
  {
    super(ElasticDocumentController.class, ElasticQueryServiceResponseModel.class);
    this.elasticToResponseModelTransformer = elasticToResponseModelTransformer;
  }

  @Override
  public ElasticQueryServiceResponseModel toModel(TwitterIndexModel twitterIndexModel)
  {
    final var responseModel = elasticToResponseModelTransformer.toModel(twitterIndexModel);
    responseModel.add(
        linkTo(methodOn(ElasticDocumentController.class)
            .getDocumentById(twitterIndexModel.getId()))
            .withSelfRel());
    responseModel.add(linkTo(ElasticDocumentController.class).withRel("documents"));

    return responseModel;
  }

  public List<ElasticQueryServiceResponseModel> toModels(List<TwitterIndexModel> twitterIndexModels)
  {
    return twitterIndexModels.stream().map(this::toModel).collect(Collectors.toList());
  }
}
