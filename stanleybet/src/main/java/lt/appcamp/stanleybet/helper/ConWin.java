package lt.appcamp.stanleybet.helper;

import lt.appcamp.stanleybet.services.Api;

/**
 * Created by Tadas on 5/29/13.
 */
public class ConWin {
    //Godzila-591
    public static final String API_KEY = "Godzila-591";

    public static final String API_URL = "http://api.test.stanleybet.com:20000/content/v1/navigation/tree/0/0/en/*/today.json";//"";
    public static final String DOC_URL = "http://api.test.stanleybet.com:20000/metadata";

    public static final String BUNDLE_NAVIGATION_TREE = "bundle_navigation_tree";
    public static final String BUNDLE_SPORTS_LIST = "bundle_sports_list";
    public static final String BUNDLE_LEAGUES_LIST = "bundle_leagues_list";
    public static final String BUNDLE_COUNTRIES_LIST = "bundle_countries_list";

    public static final String BUNDLE_FAVORITE_SLIDE_BAR = "bundle_favorite_slide_bar";
    public static final String BUNDLE_FAVORITE_SLIDE_LIST = "bundle_favorite_slide_list";

    //public static final String BUNDLE_CATEGORY_TYPE = "bundle_category_type";

    //The string is hardcoded for now and only the "lat" and "long" parameter is passed on.
    //There are no requirements currently (2013 10 16), that would have the need for additional parameters to be passed 
    //from the app. Future modifications might need to change this.
    public static final String STREET_VIEW_URL = "https://maps.google.com/maps?q=&layer=c&cbp=0,0,0,0,0";
    
    public static final String BUNDLE_PLACE_LAT = "bundle_place_lat";
    public static final String BUNDLE_PLACE_LONG = "bundle_place_long";
    
    public static final double DEFAULT_PLACE_LAT = 54.02383225203062;
    public static final double DEFAULT_PLACE_LONG = 23.972023129463196;

    public static final String BUNDLE_CATEGORY_LIST = "bundle_category_list";
    public static final String BUNDLE_CATEGORY_TYPE = "bundle_category_type";
    
    public static final String BUNDLE_MODEL = "bundle_model";
    public static final String BUNDLE_MENU_POS = "menu_pos";
    public static final String BUNDLE_GENDER = "bundle_gender";
    
    public static final String BUNDLE_SEARCH_STRING = "bundle_search_string";
    
    public static final String PAYMENT_OPTION = "payment_option";    
    
    public static final int PAYMENT_OPTION_MOKEJIMAILT = 1; 
    public static final int PAYMENT_OPTION_SMSBANK = 2;    
    public static final int PAYMENT_OPTION_PAYPAL = 3;    
    public static final int PAYMENT_OPTION_WEBMONEY = 4; 
    public static final int PAYMENT_OPTION_IN_APP_SMS = 5;    
    public static final int PAYMENT_OPTION_EASYPAY = 6; 
    
	/** WebToPay project ID **/
    // You must enter your projectId here!
    public static final int PROJECT_ID = 22222;
    //public static final int PROJECT_ID = 40188; //Vilnius
    public static final String DEFAULT_CURRENCY= "EUR";
    
    public static final String LT = "lt";
    public static final String RU = "ru";
    public static final String PL = "pl";
    public static final String EN = "en";

    public static final String IMAGE_DEFAULT_BASE_URL = "http://api.test.stanleybet.com:20000/storage/";

    public static final String BUNDLE_SHOW_PURCHASE_DIALOG = "bundle_show_purchase_dialog";
    public static final String BUNDLE_GOOGLE_ACCOUNT = "bundle_google_account";
    public static final String BUNDLE_INIT_LOGIN = "bundle_init_login";


    /** ANALYTICS STUFF **/

    /**
     * Categories
     */
    public static final String GA_CAT_UI = "ui_action";

    /**
     * Action
     */
    public static final String GA_ACTION_PRESS = "press";
    public static final String GA_ACTION_CHANGE_LANGUAGE = "change_language";
    public static final String GA_ACTION_PACKAGE = "package_action";
    public static final String GA_ACTION_LOGIN = "login";

    /**
     * About
     */
    public static final String GA_ABOUT_CITY = "about_city";
    public static final String GA_ABOUT_TRANSPORT = "about_transport";
    public static final String GA_ABOUT_FACTS = "about_facts";

    /**
     * Labels
     */
    public static final String GA_LABEL_PACKAGE_BUY = "package_buy";
    public static final String GA_LABEL_PACKAGE_ACTIVATE = "package_activate";
    public static final String GA_LABEL_LANGUAGE = "language_change";

    public static final String GA_LABEL_PLACE_LIST = "place_list";
    public static final String GA_LABEL_EVENT_LIST = "event_list";
    public static final String GA_LABEL_PACKAGE_LIST = "package_list";

    public static final String GA_LABEL_LOGIN_FB = "login_facebook";
    public static final String GA_LABEL_LOGIN_GPLUS = "login_google";
    public static final String GA_LABEL_LOGIN_NATIVE = "login_native";
    public static final String GA_LABEL_LOGIN_SKIP = "login_skip";
    public static final String GA_LABEL_LOGIN_NATIVE_CREATE = "login_native_create";
    public static final String GA_LABEL_LOGIN_NATIVE_EDIT = "login_native_edit";
    public static final String GA_LABEL_REMIND_PASS = "login_remind_pass";
    public static final String GA_LABEL_LOGIN_LOGOUT = "login_logout";
}
