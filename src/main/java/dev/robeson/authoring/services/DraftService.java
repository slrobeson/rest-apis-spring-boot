package dev.robeson.authoring.services;

import dev.robeson.authoring.models.DraftResponse;
import dev.robeson.authoring.models.Invoice;
import dev.robeson.authoring.models.Item;
import dev.robeson.authoring.models.Surcharge;
import dev.robeson.authoring.repositories.ApprovalRepository;
import dev.robeson.authoring.repositories.DraftRepository;
import dev.robeson.authoring.repositories.FeedbackRepository;
import dev.robeson.authoring.repositories.RejectionRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DraftService {
    private final DraftRepository _draftRepository;
    private final ApprovalRepository _approvalRepository;
    private final FeedbackRepository _feedbackRepository;
    private final RejectionRepository _rejectionRepository;

    private static final float THRESHOLD = 1000;

    public DraftService(DraftRepository draftRepository, ApprovalRepository approvalRepository, FeedbackRepository feedbackRepository, RejectionRepository rejectionRepository){
        _draftRepository = draftRepository;
        _approvalRepository = approvalRepository;
        _feedbackRepository = feedbackRepository;
        _rejectionRepository = rejectionRepository;
    }

    public DraftResponse create(String author, Invoice invoice) {
        return  _draftRepository.create(author, invoice);
    }

    public List<DraftResponse> findPendingByAuthor(String author) {
        var drafts = _draftRepository.findByAuthor(author);

        List<DraftResponse> pending = new ArrayList<>();

        for (DraftResponse draft : drafts) {
            // if any feedbacks exist, then NOT pending
            var feedbacks = _feedbackRepository.findForDraft(draft.id());
            if (feedbacks != null && !feedbacks.isEmpty()){
                continue;
            }

            // if any rejections exist, then NOT pending
            var rejections = _rejectionRepository.findForDraft(draft.id());
            if (rejections != null && !rejections.isEmpty()){
                continue;
            }

            var approvals = _approvalRepository.findByDraft(draft.id());
            var total = calculateInvoiceTotal(draft.invoice());

            // Check if total is greater than the threshold
            if (total > THRESHOLD){
                // if 2 or more approvals, then NOT pending
                if (approvals != null && approvals.size() >= 2){
                    continue;
                }
            } else {
                // if any approvals exist, then NOT pending
                if (approvals != null && !approvals.isEmpty()){
                    continue;
                }
            }

            pending.add(draft);
        }

        return pending;
    }

    private float calculateInvoiceTotal(Invoice invoice) {
        float total = 0;

        for (Item item : invoice.items()) {
            total += (item.cost() * item.quantity());
        }

        for (Surcharge surcharge : invoice.surcharges()){
            total += (surcharge.cost() * surcharge.quantity());
        }

        return total;
    }
}
