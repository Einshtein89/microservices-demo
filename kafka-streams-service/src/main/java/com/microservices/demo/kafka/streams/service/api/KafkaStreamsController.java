package com.microservices.demo.kafka.streams.service.api;

import jakarta.validation.constraints.NotEmpty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.demo.kafka.streams.service.model.KafkaStreamsResponseModel;
import com.microservices.demo.kafka.streams.service.runner.StreamsRunner;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/", produces = "application/vnd.api.v1+json")
public class KafkaStreamsController
{
  private static final Logger LOG = LoggerFactory.getLogger(KafkaStreamsController.class);
  private final StreamsRunner<String, Long> kafkaStreamsRunner;

  public KafkaStreamsController(StreamsRunner<String, Long> kafkaStreamsRunner)
  {
    this.kafkaStreamsRunner = kafkaStreamsRunner;
  }

  @GetMapping("get-word-count-by-word/{word}")
  @Operation(summary = "Get word count by word.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successful response.", content = {
          @Content(mediaType = "application/vnd.api.v1+json",
              schema = @Schema(implementation = KafkaStreamsResponseModel.class)
          )
      }),
      @ApiResponse(responseCode = "400", description = "Not found."),
      @ApiResponse(responseCode = "500", description = "Internal server error.")
  })
  public @ResponseBody ResponseEntity<KafkaStreamsResponseModel> getWordCountByWord(@PathVariable @NotEmpty String word)
  {
    final var wordCount = kafkaStreamsRunner.getValueByKey(word);
    LOG.info("Got word count {} by word: {}", wordCount, word);

    return ResponseEntity.ok(KafkaStreamsResponseModel
        .builder()
        .word(word)
        .wordCount(wordCount)
        .build());
  }
}
