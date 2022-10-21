package dev.robeson.authoring.models;

public record FeedbackResponse(String id, DraftResponse draft, String actor, String feedback) {
}
