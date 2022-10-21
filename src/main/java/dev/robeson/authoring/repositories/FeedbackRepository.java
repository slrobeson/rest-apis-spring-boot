package dev.robeson.authoring.repositories;

import dev.robeson.authoring.models.FeedbackResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FeedbackRepository {
    public List<FeedbackResponse> findForDraft(String draft) {
        return null;
    }
}
