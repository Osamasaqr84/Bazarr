package com.bazar.bazars;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.Set;


public class PreferenceHelper {

	private SharedPreferences app_prefs;
	private final String CITY_ID = "cityId";
    private final String SENDER_ID = "sender_id";
	private final String YEAR_PREFRENCE = "yearPref";
	private final String IMAGE_PREFRENCE = "imagePref";
	private final String FRAG_Position = "frag_position";
    private final String CITY_SELECTION = "city_selection";
	private final String BIG_OR_SMALL = "bigorsmall";
    private final String FILTER_TYPE = "filter_type";
	private final String USER_ID = "user_id";
	private final String API_TOKEN = "api_token";
	public static String app_ref = "harag";
	public static String adid = "adid";
	public static String Notificationid = "notificationid";
	public static String OpenNotificationActivity = "opennotificationacitvity";

	private Context context;
	public static String Uri = "rui";

	private final String NEXT_URL = "next_url";
	public Set getUri() {
		return app_prefs.getStringSet(Uri, null);
	}

	public void setUri(Set<String> uri){
		Editor edit = app_prefs.edit();
		edit.putStringSet(Uri, uri);
		edit.apply();
	}
	public PreferenceHelper(Context context) {
		app_prefs = context.getSharedPreferences(app_ref,
				Context.MODE_PRIVATE);
		this.context = context;
	}
    public void putCitID(String cityID) {
        Editor edit = app_prefs.edit();
        edit.putString(CITY_ID, cityID);
        edit.apply();
    }
    public String getCityId() {
        return app_prefs.getString(CITY_ID, "0");
    }
	public void setOpenNotificationActivity(String cityID) {
		Editor edit = app_prefs.edit();
		edit.putString(OpenNotificationActivity, cityID);
		edit.apply();
	}

	public void setSellerid(String cityID) {
		Editor edit = app_prefs.edit();
		edit.putString("sss", cityID);
		edit.apply();
	}
	public String getSellerId() {
		return app_prefs.getString("sss", "0");
	}


	public String getOpenNotificationActivity() {
		return app_prefs.getString(OpenNotificationActivity, "0");
	}
    public void putImagePreference(String imagePref) {
        Editor edit = app_prefs.edit();
        edit.putString(IMAGE_PREFRENCE, imagePref);
        edit.apply();
    }
    public String getImagePreference() {
        return app_prefs.getString(IMAGE_PREFRENCE, "0");
    }
	public void putNotificationId(String imagePref) {
		Editor edit = app_prefs.edit();
		edit.putString(Notificationid , imagePref);
		edit.apply();
	}
	public String getNotificationId() {
		return app_prefs.getString(Notificationid , "0");
	}
	public void putYearPreference(String yearPRef) {
		Editor edit = app_prefs.edit();
		edit.putString(YEAR_PREFRENCE, yearPRef);
		edit.apply();
	}
	public String getYearPreference() {
		return app_prefs.getString(YEAR_PREFRENCE, "0");
	}

	public void putUserID(String selection){
		Editor edit = app_prefs.edit();
		edit.putString(USER_ID,selection);
		edit.apply();
	}
	public String getUserID(){
		return app_prefs.getString(USER_ID,null);
	}

	public void putSenderID(String adid){
		Editor edit = app_prefs.edit();
		edit.putString(SENDER_ID,adid);
		edit.apply();
	}public String getAdID(){
		return app_prefs.getString(USER_ID,null);
	}

	public void putAdID(String adida){
		Editor edit = app_prefs.edit();
		edit.putString(SENDER_ID,adida);
		edit.apply();
	}
	public String getSenderID(){
		return app_prefs.getString(SENDER_ID,null);
	}

	public String getAPI_TOKEN() {
		return app_prefs.getString(API_TOKEN, null);
	}

	public void setAPI_TOKEN(String API_TOKENs) {
		Editor edit = app_prefs.edit();
		edit.putString(API_TOKEN, API_TOKENs);
		edit.apply();
	}

	public void setNEXT_URL(String nextUrlt){
		Editor edit = app_prefs.edit();
		edit.putString(NEXT_URL,nextUrlt);
		edit.apply();
	}

	public String getUserName(){
		return app_prefs.getString("name",null);
	}
    public void putShowType(int iD){
        Editor edit = app_prefs.edit();
        edit.putInt(BIG_OR_SMALL,iD);
        edit.apply();
    }

	public void putUserName(String name){
		Editor edit = app_prefs.edit();
		edit.putString("name",name);
		edit.apply();
	}

	public String getNEXT_URL(){
		return app_prefs.getString(NEXT_URL,null);
	}


    public int getShowType(){
        return app_prefs.getInt(BIG_OR_SMALL,0);
    }

	public void clearRequestData() {

	}
	public void putCitySelection(int selection){
		Editor edit = app_prefs.edit();
		edit.putInt(CITY_SELECTION,selection);
		edit.apply();
	}
	public int getCitySelection(){
		return app_prefs.getInt(CITY_SELECTION,0);
	}
	public void Logout() {
		clearRequestData();

	}
}
