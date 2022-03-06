package com.achermashentsev;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {IntegrationConfig.class})
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class WorkerPlanningServiceIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Test
    public void createNewWorker()
            throws Exception {
        createNewWorker("John", "Doe");
        JSONArray array = getWorkers();
        assertEquals(1, array.length());
        var worker = (JSONObject) array.get(0);
        assertEquals("John", worker.getString("firstName"));
        assertEquals("Doe", worker.getString("lastName"));
        assertEquals(0, worker.getJSONArray("shifts").length());
    }

    @Test
    public void deleteWorker()
            throws Exception {
        var id = createNewWorker("John", "Doe").getInt("id");
        mockMvc.perform(delete("/worker/" + id)
                                .contentType(APPLICATION_JSON)
                                .accept(APPLICATION_JSON))
               .andExpect(status().isOk())
               .andReturn().getResponse().getContentAsString();
        var workers = getWorkers();
        assertEquals(0, workers.length());
    }

    @Test
    public void addAndRemoveShift()
            throws Exception {
        var workerId = createNewWorker("John", "Doe").getInt("id");
        var shiftDate = "2020-01-02";
        var shift = new JSONObject(createNewShift(workerId, shiftDate)
                                          .andExpect(status().isOk())
                                          .andReturn().getResponse().getContentAsString());
        var shifts = new JSONArray(mockMvc.perform(get(format("/worker/%s/shift", workerId)))
                                          .andExpect(status().isOk())
                                          .andReturn().getResponse().getContentAsString());
        assertEquals(1, shifts.length());
        assertEquals(shift.toString(), shifts.get(0).toString());
        mockMvc.perform(delete(format("/worker/%s/shift/%s", workerId, shiftDate)))
               .andExpect(status().isOk());
        shifts = new JSONArray(mockMvc.perform(get(format("/worker/%s/shift", workerId)))
                                      .andExpect(status().isOk())
                                      .andReturn().getResponse().getContentAsString());
        assertEquals(0, shifts.length());
    }

    @Test
    public void createWorkersConcurrently()
            throws Exception {
        var threadsNumber = 10;
        var workersPerThread = 100;
        var executor = Executors.newFixedThreadPool(threadsNumber);
        var latch = new CountDownLatch(threadsNumber);
        for (int i = 0; i < threadsNumber; i++) {
            executor.submit(() -> {
                try {
                    for (int j = 0; j < workersPerThread; j++) {
                        createNewWorker("Worker", "Walker");
                    }
                } catch (Exception e) {
                    fail("Exception when create new worker", e);
                } finally {
                    latch.countDown();
                }
            });
        }
        assertTrue(latch.await(1, TimeUnit.MINUTES), "Latch timeout");
        var workers = getWorkers();
        var workersIds = new HashSet<Integer>();
        for (int i = 0; i < workers.length(); i++) {
            workersIds.add(((JSONObject) workers.get(i)).getInt("id"));
        }
        assertEquals(threadsNumber * workersPerThread, workersIds.size());
    }

    @Test
    public void cantFindNotExistedWorker()
            throws Exception {
        mockMvc.perform(get("/worker/1"))
               .andExpect(status().isNotFound());
    }

    @Test
    public void cantAssignShiftTwice()
            throws Exception {
        var workerId = createNewWorker("John", "Doe").getInt("id");
        var shiftDate = "2020-01-02";
        createNewShift(workerId, shiftDate).andExpect(status().isOk());
        createNewShift(workerId, shiftDate).andExpect(status().isBadRequest());
    }

    @Test
    public void cantDeleteShiftFromOtherDay()
            throws Exception {
        var workerId = createNewWorker("John", "Doe").getInt("id");
        var shiftDate = "2020-01-02";
        var wrongShiftDate = "2021-01-01";
        createNewShift(workerId, shiftDate).andExpect(status().isOk());
        mockMvc.perform(delete(format("/worker/%s/shift/%s", workerId, wrongShiftDate)))
                .andExpect(status().isNotFound());

    }

    private ResultActions createNewShift(int workerId, String shiftDate)
            throws Exception {
        return mockMvc.perform(post(format("/worker/%s/shift", workerId))
                                                      .contentType(APPLICATION_JSON)
                                                      .accept(APPLICATION_JSON)
                                                      .content(format("{\"date\":\"%s\", \"slot\":\"EIGHT\"}", shiftDate)));
    }


    private JSONObject createNewWorker(String firstName, String lastName)
            throws Exception {
        return new JSONObject(mockMvc.perform(post("/worker")
                                                      .contentType(APPLICATION_JSON)
                                                      .accept(APPLICATION_JSON)
                                                      .content(format("{\"firstName\": \"%s\", \"lastName\": \"%s\"}", firstName, lastName)))
                                     .andExpect(status().isOk())
                                     .andReturn().getResponse().getContentAsString());
    }

    private JSONArray getWorkers()
            throws Exception {
        return new JSONArray(mockMvc.perform(get("/worker"))
                                    .andExpect(status().isOk())
                                    .andReturn().getResponse().getContentAsString());
    }

}
