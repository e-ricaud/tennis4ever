package com.ericaud.tennis4ever.api.resource;

import com.ericaud.tennis4ever.service.ScoringService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(
            summary = "Get score details",
            description = "Provides the score details based on the scoring input sequence.",
            parameters = {
            @Parameter(
                    name = "scoringInput",
                    description = "The scoring sequence as a string containing 'A' and 'B'. For example: 'AABAA'.",
                    required = false,
                    schema = @Schema(type = "string"),
                    examples = @ExampleObject(
                            name = "Example Scoring Input sequence, here A wins the game"
                            + """
                                \nPlayer A : 15 / Player B : 0
                                \nPlayer A : 30 / Player B : 0
                                \nPlayer A : 30 / Player B : 15
                                \nPlayer A : 40 / Player B : 15
                                \nPlayer A wins the game
                            """,
                            value = "AABAA",
                            summary = "Example of a scoring input sequence"
                    )
            )
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid scoring input",
                    content = @Content(
                            schema = @Schema(implementation = String.class),
                            examples = {
                                @ExampleObject(
                                    name = "Example sequence AABAAA, is invalid because game finishing before end of sequence",
                                    value = "Scoring input invalid because game finishing before end of sequence"
                                )
                            }
                    )
            )
    })
    @GetMapping("/scoreDetail")
    public String getScoreDetail(@RequestParam(required = false) String scoringInput) {
        try {
            return scoringService.getScoringDetail(scoringInput);
        } catch (IllegalArgumentException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }

}
