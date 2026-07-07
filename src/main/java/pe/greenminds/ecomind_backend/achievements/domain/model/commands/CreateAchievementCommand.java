package pe.greenminds.ecomind_backend.achievements.domain.model.commands;

public record CreateAchievementCommand(String name, String description, String type, Integer thresholdValue) {}
