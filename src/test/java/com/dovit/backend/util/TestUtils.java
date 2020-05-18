package com.dovit.backend.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Ramón París
 * @since 30-03-20
 */
public class TestUtils {

  private static Gson gson =
      new GsonBuilder()
          .registerTypeAdapter(
              LocalDate.class,
              (JsonSerializer<LocalDate>)
                  (date, type, jsonSerializationContext) ->
                      new JsonPrimitive(date.format(DateTimeFormatter.ISO_LOCAL_DATE)))
          .create();

  public static void testGetRequest(
      MockMvc mockMvc, String endpoint, MultiValueMap<String, String> params) {
    MvcResult mvcResult;
    try {
      mvcResult =
          mockMvc
              .perform(
                  MockMvcRequestBuilders.get("http://localhost:8080/api" + endpoint).params(params))
              .andExpect(status().is2xxSuccessful())
              .andReturn();
      assertNotNull(mvcResult.getResponse().getContentAsString());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void testGetRequest(MockMvc mockMvc, String endpoint) {
    MvcResult mvcResult;
    try {
      mvcResult =
          mockMvc
              .perform(MockMvcRequestBuilders.get("http://localhost:8080/api" + endpoint))
              .andExpect(status().is2xxSuccessful())
              .andReturn();
      assertNotNull(mvcResult.getResponse().getContentAsString());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void testPostRequest(MockMvc mockMvc, String endpoint, Object requestObject) {
    String request = gson.toJson(requestObject);
    MvcResult mvcResult;
    try {
      mvcResult =
          mockMvc
              .perform(
                  MockMvcRequestBuilders.post("http://localhost:8080/api" + endpoint)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(request))
              .andExpect(status().is2xxSuccessful())
              .andReturn();
      assertNotNull(mvcResult.getResponse().getContentAsString());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void testPutRequest(MockMvc mockMvc, String endpoint, Object requestObject) {
    String request = gson.toJson(requestObject);
    MvcResult mvcResult;
    try {
      mvcResult =
          mockMvc
              .perform(
                  MockMvcRequestBuilders.put("http://localhost:8080/api" + endpoint)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(request))
              .andExpect(status().is2xxSuccessful())
              .andReturn();
      assertNotNull(mvcResult.getResponse().getContentAsString());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void testPatchRequest(MockMvc mockMvc, String endpoint, Object requestObject) {
    String request = gson.toJson(requestObject);
    MvcResult mvcResult;
    try {
      mvcResult =
          mockMvc
              .perform(
                  MockMvcRequestBuilders.patch("http://localhost:8080/api" + endpoint)
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(request))
              .andExpect(status().is2xxSuccessful())
              .andReturn();
      assertNotNull(mvcResult.getResponse().getContentAsString());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void testDeleteRequest(MockMvc mockMvc, String endpoint) {
    MvcResult mvcResult;
    try {
      mvcResult =
          mockMvc
              .perform(MockMvcRequestBuilders.delete("http://localhost:8080/api" + endpoint))
              .andExpect(status().is2xxSuccessful())
              .andReturn();
      assertNotNull(mvcResult.getResponse().getContentAsString());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
