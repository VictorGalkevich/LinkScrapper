package edu.java.bot.controller;

import edu.java.bot.service.UpdateService;
import edu.java.dto.request.LinkUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/updates")
@RequiredArgsConstructor
@Slf4j
public class UpdateController {
    private final UpdateService updateService;

    @PostMapping
    public void proceedUpdates(@RequestBody LinkUpdate update) {
        log.error("Pizdec");
        updateService.proceedUpdates(update);
    }
}
