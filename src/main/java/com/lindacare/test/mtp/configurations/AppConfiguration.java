package com.lindacare.test.mtp.configurations;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.loginbox.heroku.config.HerokuConfiguration;

import javax.validation.Valid;

public class AppConfiguration extends HerokuConfiguration {

  @Valid
  @JsonProperty("kafka")
  private final KafkaConfig kafkaConfig = new KafkaConfig();

  public KafkaConfig getKafkaConfig() {
    return kafkaConfig;
  }
}
