package dev.robeson.authoring.repositories;

import dev.robeson.authoring.models.RejectionResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RejectionRepository {

    public List<RejectionResponse> findForDraft(String draft) {
        return null;
    }
}
