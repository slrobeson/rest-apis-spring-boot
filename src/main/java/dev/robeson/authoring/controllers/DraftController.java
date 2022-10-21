package dev.robeson.authoring.controllers;

import dev.robeson.authoring.models.DraftCreate;
import dev.robeson.authoring.models.DraftResponse;
import dev.robeson.authoring.services.DraftService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoice/authoring/v1")
public class DraftController {
    private final DraftService _draftService;

    public DraftController(DraftService draftService){
        _draftService = draftService;
    }

    @PostMapping(path = "/drafts")
    @ResponseStatus(HttpStatus.CREATED)
    public DraftResponse create(@RequestBody final DraftCreate request){
        return _draftService.create(request.author(), request.invoice());
    }

    @GetMapping(path = "/pending")
    public List<DraftResponse> findPending(@RequestParam(name = "filter.author") String author){
        return _draftService.findPendingByAuthor(author);
    }
}
