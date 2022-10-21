package dev.robeson.authoring.repositories;

import dev.robeson.authoring.models.DraftResponse;
import dev.robeson.authoring.models.Invoice;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class DraftRepository  {
    List<DraftResponse> drafts = new ArrayList<>();

    public DraftResponse create(String author, Invoice invoice) {
        var draft = new DraftResponse(UUID.randomUUID().toString(), author, invoice);
        drafts.add(draft);
        return draft;
    }

    public List<DraftResponse> findByAuthor(String author) {
        return drafts.stream().filter(stream -> stream.author().equals(author)).toList();
    }

    public DraftResponse findById(String id) {
        return drafts.stream().filter(stream -> stream.id().equals(id)).findFirst().orElse(null);
    }
}
