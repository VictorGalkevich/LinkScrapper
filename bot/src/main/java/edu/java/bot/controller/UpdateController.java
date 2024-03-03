package edu.java.bot.controller;

import edu.java.bot.service.UpdateService;
import edu.java.dto.request.LinkUpdate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/updates")
@RequiredArgsConstructor
public class UpdateController {
    private final UpdateService updateService;

    @PostMapping
    @Operation(summary = "Send updates")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
        @ApiResponse(responseCode = "200", description = "Updates proceeded")})
    public void proceedUpdates(LinkUpdate update) {
        updateService.proceedUpdates(update);
    }
}
