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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(value = "/documents", produces = "application/vnd.api.v1+json")
public class ElasticDocumentController
{
  private static final Logger LOG = LoggerFactory.getLogger(ElasticDocumentController.class);

  private final ElasticQueryService elasticQueryService;

  public ElasticDocumentController(ElasticQueryService elasticQueryService)
  {
    this.elasticQueryService = elasticQueryService;
  }

  @Operation(summary = "Get all elastic documents")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "success", content = {
          @Content(mediaType = "application/vnd.api.v1+json",
              schema = @Schema(implementation = ElasticQueryServiceResponseModel.class))
      }),
      @ApiResponse(responseCode = "400", description = "Not found."),
      @ApiResponse(responseCode = "500", description = "Internal server error.")
  })
  @GetMapping
  public ResponseEntity<List<ElasticQueryServiceResponseModel>> getAllDocument()
  {
    List<ElasticQueryServiceResponseModel> response = elasticQueryService.getAllDocuments();
    LOG.info("ElasticSearch returned {} documents.", response.size());
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Get elastic document by id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "success", content = {
          @Content(mediaType = "application/vnd.api.v1+json",
              schema = @Schema(implementation = ElasticQueryServiceResponseModel.class))
      }),
      @ApiResponse(responseCode = "400", description = "Not found."),
      @ApiResponse(responseCode = "500", description = "Internal server error.")
  })
  @GetMapping("/{id}")
  public ResponseEntity<ElasticQueryServiceResponseModel> getDocumentById(@PathVariable @NotEmpty String id)
  {
    ElasticQueryServiceResponseModel response = elasticQueryService.getDocumentById(id);
    LOG.info("ElasticSearch returned document with id {}", id);

    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Get elastic document by id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "success", content = {
          @Content(mediaType = "application/vnd.api.v2+json",
              schema = @Schema(implementation = ElasticQueryServiceResponseModelV2.class))
      }),
      @ApiResponse(responseCode = "400", description = "Not found."),
      @ApiResponse(responseCode = "500", description = "Internal server error.")
  })
  @GetMapping(value = "/{id}", produces = "application/vnd.api.v2+json")
  public ResponseEntity<ElasticQueryServiceResponseModelV2> getDocumentByIdV2(@PathVariable @NotEmpty String id)
  {
    ElasticQueryServiceResponseModel response = elasticQueryService.getDocumentById(id);

    LOG.info("ElasticSearch returned document with id {}", id);

    return ResponseEntity.ok(getV2Model(response));
  }

  @Operation(summary = "Get elastic document by text")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "success", content = {
          @Content(mediaType = "application/vnd.api.v1+json",
              schema = @Schema(implementation = ElasticQueryServiceResponseModel.class))
      }),
      @ApiResponse(responseCode = "400", description = "Not found."),
      @ApiResponse(responseCode = "500", description = "Internal server error.")
  })
  @PostMapping(path = "/get-document-by-text")
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
        .text2("V2 text")
        .userId(responseModel.getUserId())
        .build().add(responseModel.getLinks());
  }
}
