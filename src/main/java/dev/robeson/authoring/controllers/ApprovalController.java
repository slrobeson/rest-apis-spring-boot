package dev.robeson.authoring.controllers;

import dev.robeson.authoring.models.ApprovalCreate;
import dev.robeson.authoring.models.ApprovalResponse;
import dev.robeson.authoring.services.ApprovalService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invoice/authoring/v1")
public class ApprovalController {
    private final ApprovalService _approvalService;

    public ApprovalController(ApprovalService approvalService){
        _approvalService = approvalService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/approvals")
    public ApprovalResponse create(@RequestBody ApprovalCreate request){
        return _approvalService.create(request.actor(), request.draft());
    }
}
