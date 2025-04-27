package com.ericaud.tennis4ever.api.resource;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/scoring")
public class ScoringResource {

    @GetMapping("/winner")
    public String getWinner(@RequestParam(required = false) String scoringInput) {
        return "Winner is: " + scoringInput;
    }

}
