package com.lindacare.test.mtp;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Uninterruptibles;
import com.lindacare.test.mtp.consumer.Consumer;
import com.lindacare.test.mtp.model.Message;
import com.lindacare.test.mtp.producer.Producer;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.lang.String.format;

@Path("/")
public class Resource {
  private final Producer producer;

  private final Consumer consumer;

  public Resource(Producer producer, Consumer consumer) {
    this.producer = producer;
    this.consumer = consumer;
  }

  @GET
  @Path("messages")
  @Produces(MediaType.APPLICATION_JSON)
  @Timed
  public List<Message> getMessages() {
      //TODO receive parameter to decide the number of messages to return
      if (consumer.getMessages().size() > 0) {
          return Lists.reverse(consumer.getMessages()).subList(0,(consumer.getMessages().size() > 10?9:consumer.getMessages().size()));
      }
      return Collections.emptyList();
  }

  @GET
  @Path("chart_messages")
  @Produces(MediaType.APPLICATION_JSON)
  @Timed
  public Map<String,Integer> getChartMessages() {

      Map<String, Integer> frequency = new HashMap<>();

      for(Message m:consumer.getMessages())
      {
          Integer freq = frequency.get(m.getCurrencyFrom() + "/" + m.getCurrencyTo());
          if(freq==null) freq=0;
          frequency.put(m.getCurrencyFrom() + "/" + m.getCurrencyTo(),freq+1);
      }

      return frequency;

  }



  @POST
  @Path("messages")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Timed
  public String addMessage(String message) throws TimeoutException, ExecutionException, IOException {
      ObjectMapper mapper = new ObjectMapper();
      Message m = mapper.readValue(message, Message.class);
    Uninterruptibles.getUninterruptibly(producer.send(m), 20, TimeUnit.SECONDS);
    return format("received message: %s", message);
  }
}