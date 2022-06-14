package ua.epam.final_project.entity;

import java.io.Serializable;

public class UserEdition implements Serializable {

    private int userId;
    private int editionId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEditionId() {
        return editionId;
    }

    public void setEditionId(int editionId) {
        this.editionId = editionId;
    }
}
