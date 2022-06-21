package ua.epam.final_project.controller.command;

public enum AccessLevel {
    ADMIN (3),
    USER (2),
    GUEST(1),
    BLOCKED(0);

    private final int level;

    AccessLevel(int s) {
        this.level = s;
    }

    public int getLevel() {
        return level;
    }
}
