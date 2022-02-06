package ua.epam.final_project.util;

public final class UrlLayoutConstants {

    private UrlLayoutConstants() {
    }

    public static final String MAIN_URL = "/";
    public static final String ABOUT_US_URL = "/about";

    public static final String CABINET_URL = "/cabinet";
    public static final String USER_SETTINGS_URL = "/cabinet/user_settings";
    public static final String EDITION_LIST_URL = "/cabinet/edition_list";
    public static final String ADD_NEW_EDITION_URL = "/cabinet/edition_list/add_new_edition";
    public static final String DELETE_EDITION_URL = "/cabinet/edition_list/delete_edition";

    public static final String USER_LIST_URL = "/cabinet/user_list";
    public static final String DELETE_USER_URL = "/cabinet/user_list/delete_user";

    public static final String SUBSCRIPTION_LIST_URL = "/cabinet/subscriptions";
    public static final String WALLET_URL = "/cabinet/wallet";
    public static final String FILL_UP_WALLET_URL = "/cabinet/wallet/fill_up";

    public static final String LOGIN_URL = "/login";
    public static final String LOGIN_SUCCESS_URL = "/login/successful_login";
    public static final String LOGIN_FAILURE_URL = "/login/unsuccessful_login";

    public static final String USER_REGISTRATION_URL = "/new_account";
    public static final String REGISTRATION_SUCCESS_URL = "/new_account/successful_registration";
    public static final String REGISTRATION_FAILURE_URL = "/new_account/unsuccessful_registration";

    public static final String LOGOUT_URL = "/logout";


}
