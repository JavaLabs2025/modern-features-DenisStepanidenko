package org.lab.model.permission;

public enum Permission {

    CREATE_PROJECT() {
        @Override
        public boolean allowedForManager() {
            return true;
        }

        @Override
        public boolean allowedForTeamLead() {
            return false;
        }

        @Override
        public boolean allowedForDeveloper() {
            return false;
        }

        @Override
        public boolean allowedForQA() {
            return false;
        }

    },

    ASSIGN_TEAMLEAD() {
        @Override
        public boolean allowedForManager() {
            return true;
        }

        @Override
        public boolean allowedForTeamLead() {
            return false;
        }

        @Override
        public boolean allowedForDeveloper() {
            return false;
        }

        @Override
        public boolean allowedForQA() {
            return false;
        }
    },

    ASSIGN_DEVELOPER() {
        @Override
        public boolean allowedForManager() {
            return true;
        }

        @Override
        public boolean allowedForTeamLead() {
            return false;
        }

        @Override
        public boolean allowedForDeveloper() {
            return false;
        }

        @Override
        public boolean allowedForQA() {
            return false;
        }
    },

    ASSIGN_QA() {
        @Override
        public boolean allowedForManager() {
            return true;
        }

        @Override
        public boolean allowedForTeamLead() {
            return false;
        }

        @Override
        public boolean allowedForDeveloper() {
            return false;
        }

        @Override
        public boolean allowedForQA() {
            return false;
        }
    },

    CREATE_MILESTONE() {
        @Override
        public boolean allowedForManager() {
            return true;
        }

        @Override
        public boolean allowedForTeamLead() {
            return false;
        }

        @Override
        public boolean allowedForDeveloper() {
            return false;
        }

        @Override
        public boolean allowedForQA() {
            return false;
        }
    },

    CHANGE_STATUS_OF_MILESTONE() {
        @Override
        public boolean allowedForManager() {
            return true;
        }

        @Override
        public boolean allowedForTeamLead() {
            return false;
        }

        @Override
        public boolean allowedForDeveloper() {
            return false;
        }

        @Override
        public boolean allowedForQA() {
            return false;
        }
    },

    CREATE_TICKET() {
        @Override
        public boolean allowedForManager() {
            return true;
        }

        @Override
        public boolean allowedForTeamLead() {
            return true;
        }

        @Override
        public boolean allowedForDeveloper() {
            return false;
        }

        @Override
        public boolean allowedForQA() {
            return false;
        }
    },

    ATTACH_DEVELOPER_TO_TICKET() {
        @Override
        public boolean allowedForManager() {
            return true;
        }

        @Override
        public boolean allowedForTeamLead() {
            return true;
        }

        @Override
        public boolean allowedForDeveloper() {
            return false;
        }

        @Override
        public boolean allowedForQA() {
            return false;
        }
    },

    CHECK_TICKET() {
        @Override
        public boolean allowedForManager() {
            return true;
        }

        @Override
        public boolean allowedForTeamLead() {
            return true;
        }

        @Override
        public boolean allowedForDeveloper() {
            return false;
        }

        @Override
        public boolean allowedForQA() {
            return false;
        }

    },

    DO_TICKET() {
        @Override
        public boolean allowedForManager() {
            return false;
        }

        @Override
        public boolean allowedForTeamLead() {
            return false;
        }

        @Override
        public boolean allowedForDeveloper() {
            return true;
        }

        @Override
        public boolean allowedForQA() {
            return false;
        }
    },

    CREATE_MESSAGE_ABOUT_MISTAKE() {
        @Override
        public boolean allowedForManager() {
            return false;
        }

        @Override
        public boolean allowedForTeamLead() {
            return false;
        }

        @Override
        public boolean allowedForDeveloper() {
            return true;
        }

        @Override
        public boolean allowedForQA() {
            return true;
        }
    },

    FIXED_MESSAGE_ABOUT_MISTAKE() {
        @Override
        public boolean allowedForManager() {
            return false;
        }

        @Override
        public boolean allowedForTeamLead() {
            return false;
        }

        @Override
        public boolean allowedForDeveloper() {
            return true;
        }

        @Override
        public boolean allowedForQA() {
            return false;
        }
    },

    TESTING_PROJECT() {
        @Override
        public boolean allowedForManager() {
            return false;
        }

        @Override
        public boolean allowedForTeamLead() {
            return false;
        }

        @Override
        public boolean allowedForDeveloper() {
            return false;
        }

        @Override
        public boolean allowedForQA() {
            return true;
        }
    },

    CHECK_FIX() {
        @Override
        public boolean allowedForManager() {
            return false;
        }

        @Override
        public boolean allowedForTeamLead() {
            return false;
        }

        @Override
        public boolean allowedForDeveloper() {
            return false;
        }

        @Override
        public boolean allowedForQA() {
            return true;
        }
    };


    public abstract boolean allowedForManager();

    public abstract boolean allowedForTeamLead();

    public abstract boolean allowedForDeveloper();

    public abstract boolean allowedForQA();

}
