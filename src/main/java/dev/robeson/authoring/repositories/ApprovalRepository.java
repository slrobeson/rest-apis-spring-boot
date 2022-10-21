package dev.robeson.authoring.repositories;

import dev.robeson.authoring.models.ApprovalResponse;
import dev.robeson.authoring.models.DraftResponse;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class ApprovalRepository{
    List<ApprovalResponse> approvals = new ArrayList<>();

    public ApprovalResponse create(String actor, DraftResponse draft) {
        var approval = new ApprovalResponse(UUID.randomUUID().toString(), draft, actor);
        approvals.add(approval);
        return approval;
    }

    public List<ApprovalResponse> findByDraft(String draft) {
        return approvals.stream().filter(stream -> stream.draft().id().equals(draft)).toList();
    }
}
