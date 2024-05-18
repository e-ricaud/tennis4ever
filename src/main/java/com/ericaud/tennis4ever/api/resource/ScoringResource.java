package com.ericaud.tennis4ever.api.resource;

import com.ericaud.tennis4ever.service.ScoringService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/scoring")
public class ScoringResource {

    private final ScoringService scoringService;

    public ScoringResource(ScoringService scoringService) {
        this.scoringService = scoringService;
    }

    @GetMapping("/scoringDetail")
    public String getScoringDetail(@RequestParam(required = false) String scoringInput) {
        try {
            return scoringService.getScoringDetail(scoringInput);
        } catch (IllegalArgumentException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }

    }

}
