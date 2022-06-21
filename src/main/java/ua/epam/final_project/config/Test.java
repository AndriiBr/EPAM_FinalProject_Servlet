package ua.epam.final_project.config;

import ua.epam.final_project.controller.command.AccessLevel;

import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<AccessLevel> accessLevelList = Arrays.asList(AccessLevel.ADMIN, AccessLevel.USER);

        int targetRole = 1;

        boolean contain = accessLevelList.stream().anyMatch(accessLevel -> accessLevel.getLevel() == targetRole);

        System.out.println(contain);
    }
}
