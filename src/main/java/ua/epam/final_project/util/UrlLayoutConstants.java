package ua.epam.final_project.util;

public final class UrlLayoutConstants {

    private UrlLayoutConstants() {}

    public static final String MAIN_URL = "/";

    public static final String ABOUT_US_URL = "/about";

    public static final String LOGIN_URL = "/login";
    public static final String LOGIN_SUCCESS_URL = "/login/successful_login";
    public static final String LOGIN_FAILURE_URL = "/login/unsuccessful_login";
    public static final String LOGOUT_URL = "/logout";

    public static final String USER_REGISTRATION_URL = "/new_account";
    public static final String REGISTRATION_SUCCESS_URL = "/new_account/successful_registration";
    public static final String REGISTRATION_FAILURE_URL = "/new_account/unsuccessful_registration";

    public static final String EDITION_LIST_URL = "/edition_list";
    public static final String BUY_EDITION_URL = "/edition_list/buy_edition";
    public static final String BUY_EDITION_SUCCESS_URL = "/edition_list/buy_edition/success";
    public static final String BUY_EDITION_FAILURE_URL = "/edition_list/buy_edition/failure";

    public static final String CABINET_URL = "/cabinet";

    public static final String ADMIN_CONSOLE_URL = "/cabinet/admin_console";
    public static final String ADMIN_EDITION_LIST_URL = "/cabinet/admin_console/global_edition_list";
    public static final String ADD_NEW_EDITION_URL = "/cabinet/admin_console/global_edition_list/add_new_edition";
    public static final String ADD_NEW_EDITION_SUCCESS_URL = "/cabinet/admin_console/global_edition_list/add_new_edition/success";
    public static final String ADD_NEW_EDITION_FAILURE_URL = "/cabinet/admin_console/global_edition_list/add_new_edition/failure";
    public static final String DELETE_EDITION_URL = "/cabinet/admin_console/global_edition_list/delete_edition";
    public static final String EDIT_EDITION_URL = "/cabinet/admin_console/global_edition_list/update_edition";

    public static final String USER_LIST_URL = "/cabinet/admin_console/user_list";
    @Deprecated
    public static final String DELETE_USER_URL = "/cabinet/admin_console/user_list/delete_user";
    public static final String CHANGE_USER_STATUS_URL = "/cabinet/admin_console/user_list/change_user_status";

    public static final String USER_SUBSCRIPTION_LIST_URL = "/cabinet/subscription_list";
    public static final String UNSUBSCRIBE_EDITION_LIST_URL = "/cabinet/subscription_list/unsubscribe";

    public static final String USER_SETTINGS_URL = "/cabinet/user_settings";


    public static final String WALLET_URL = "/cabinet/wallet";
    public static final String FILL_UP_WALLET_URL = "/cabinet/wallet/fill_up";

}
