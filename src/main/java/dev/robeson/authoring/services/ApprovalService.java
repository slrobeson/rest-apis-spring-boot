package dev.robeson.authoring.services;

import dev.robeson.authoring.models.ApprovalResponse;
import dev.robeson.authoring.models.DraftResponse;
import dev.robeson.authoring.repositories.ApprovalRepository;
import dev.robeson.authoring.repositories.DraftRepository;
import org.springframework.stereotype.Component;

@Component
public class ApprovalService {
    private final ApprovalRepository _approvalRepository;
    private final DraftRepository _draftRepository;

    public ApprovalService(ApprovalRepository approvalRepository, DraftRepository draftRepository){
        _approvalRepository = approvalRepository;
        _draftRepository = draftRepository;
    }

    public ApprovalResponse create(String actor, String draftId) {
        DraftResponse draft = _draftRepository.findById(draftId);
        return _approvalRepository.create(actor, draft);
    }
}
