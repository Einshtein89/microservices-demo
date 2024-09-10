package com.microservices.demo.elastic.query.service.api;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.demo.elastic.query.service.business.ElasticQueryService;
import com.microservices.demo.elastic.query.service.model.ElasticQueryServiceRequestModel;
import com.microservices.demo.elastic.query.service.model.ElasticQueryServiceResponseModel;
import com.microservices.demo.elastic.query.service.model.ElasticQueryServiceResponseModelV2;

@RestController
@RequestMapping(value = "/documents")
public class ElasticDocumentController
{
  private static final Logger LOG = LoggerFactory.getLogger(ElasticDocumentController.class);

  private final ElasticQueryService elasticQueryService;

  public ElasticDocumentController(ElasticQueryService elasticQueryService)
  {
    this.elasticQueryService = elasticQueryService;
  }

  @GetMapping("/v1")
  public ResponseEntity<List<ElasticQueryServiceResponseModel>> getAllDocument()
  {
    List<ElasticQueryServiceResponseModel> response = elasticQueryService.getAllDocuments();
    LOG.info("ElasticSearch returned {} documents.", response.size());
    return ResponseEntity.ok(response);
  }

  @GetMapping("/v1/{id}")
  public ResponseEntity<ElasticQueryServiceResponseModel> getDocumentById(@PathVariable @NotEmpty String id)
  {
    ElasticQueryServiceResponseModel response = elasticQueryService.getDocumentById(id);
    LOG.info("ElasticSearch returned document with id {}", id);

    return ResponseEntity.ok(response);
  }

  @GetMapping("/v2/{id}")
  public ResponseEntity<ElasticQueryServiceResponseModelV2> getDocumentByIdV2(@PathVariable @NotEmpty String id)
  {
    ElasticQueryServiceResponseModel response = elasticQueryService.getDocumentById(id);
    LOG.info("ElasticSearch returned document with id {}", id);

    final var v2Model = getV2Model(response);

    return ResponseEntity.ok(v2Model);
  }

  @PostMapping(path = "/v1/get-document-by-text")
  public ResponseEntity<List<ElasticQueryServiceResponseModel>> getDocumentByText(
      @RequestBody @Valid ElasticQueryServiceRequestModel requestModel)
  {
    List<ElasticQueryServiceResponseModel> response = elasticQueryService.getDocumentByText(requestModel.getText());
    LOG.info("ElasticSearch returned {} documents.", response.size());

    return ResponseEntity.ok(response);
  }

  private ElasticQueryServiceResponseModelV2 getV2Model(ElasticQueryServiceResponseModel responseModel)
  {
    return ElasticQueryServiceResponseModelV2.builder()
        .id(Long.parseLong(responseModel.getId()))
        .text(responseModel.getText())
        .userId(responseModel.getUserId())
        .createdAt(responseModel.getCreatedAt())
        .build().add(responseModel.getLinks());
  }
}
