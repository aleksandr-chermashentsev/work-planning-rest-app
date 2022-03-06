package com.achermashentsev;

import com.achermashentsev.workplanningservice.controller.WorkPlanningController;
import com.achermashentsev.workplanningservice.service.IWorkersService;
import com.achermashentsev.workplanningservice.service.WorkersService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;

@EnableAutoConfiguration
public class IntegrationConfig {
    @Bean
    public WorkPlanningController controller() {
        return new WorkPlanningController();
    }

    @Bean
    public IWorkersService workersService() {
        return new WorkersService();
    }

}
