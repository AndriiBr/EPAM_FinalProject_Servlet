package ua.epam.final_project.dao;

public enum DataBaseSelector {
    MY_SQL {
        @Override
        public String toString() {
            return "MySQL";
        }
    },
    ORACLE {
        @Override
        public String toString() {
            return "Oracle Database";
        }
    }
}
