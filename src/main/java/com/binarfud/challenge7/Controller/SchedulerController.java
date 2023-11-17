package com.binarfud.challenge7.Controller;

import com.binarfud.challenge7.Service.Impl.ScheduledService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/scheduler")
public class SchedulerController {

    @Autowired
    ScheduledService ScheduledService;

    @GetMapping("/trigger-task")
    public String triggerTask() {
        ScheduledService.scheduledTask();
        return "Scheduled task triggered manually.";
    }
}
