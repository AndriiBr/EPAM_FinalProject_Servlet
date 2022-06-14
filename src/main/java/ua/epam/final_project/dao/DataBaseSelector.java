package ua.epam.final_project.dao;

public enum DataBaseSelector {
    POSTGRES {
        @Override
        public String toString() {
            return "Postgres";
        }
    },
    ORACLE {
        @Override
        public String toString() {
            return "Oracle Database";
        }
    }
}
