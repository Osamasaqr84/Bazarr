package com.bazar.bazars.Connect_TO_Server;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.bazar.bazars.Items.AdDetails;
import com.bazar.bazars.Items.AdPhotoItem;
import com.bazar.bazars.PreferenceHelper;

/**
 * @author Hardik A Bhalodi
 */
public class ParseContent {
	private Activity activity;
	private final String KEY_SUCCESS = "success";
	private final String KEY_ERROR = "error";
	ArrayList<AdPhotoItem> photos;
	PreferenceHelper preferenceHelper;
	public ParseContent(Activity activity) {
		// TODO Auto-generated constructor stub
		this.activity = activity;
		preferenceHelper = new PreferenceHelper(activity);
	}


	public boolean isSuccess(String response) {
		if (TextUtils.isEmpty(response))
			return false;
		try {
			// AppLog.Log(Const.TAG, response);

			JSONObject jsonObject = new JSONObject(response);
			if (jsonObject.getBoolean(KEY_SUCCESS)) {
				return true;
			} else {
				Toast.makeText(activity, jsonObject.getString(KEY_ERROR), Toast.LENGTH_SHORT).show();

				return false;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public AdDetails parseAdDetail(String response, AdDetails details, ArrayList<String> photos) {



		if (TextUtils.isEmpty(response)) {
			return details;
		}
		try {

			JSONObject jsonObject = new JSONObject(response);

				JSONObject object = jsonObject.getJSONObject("post");
            AdDetails adDetails = new AdDetails();
			AdPhotoItem photoItem = new AdPhotoItem();

                        adDetails.setAdId(object.getInt("id"));
                        adDetails.setUserId(object.getString("user_id"));
                        adDetails.setCreatedDate(object.getString("created_at"));
                        adDetails.setContent(object.getString("content"));
                        adDetails.setTitle(object.getString("title"));
                        adDetails.setUsername(object.getJSONObject("user").getString("name"));
                        adDetails.setMobile(object.getJSONObject("user").getString("mobile"));
                        adDetails.setCity(object.getJSONObject("citys").getString("name"));
                        //adDetails.setImageone(object.getString("image_one"));

			JSONArray array_inages = jsonObject.getJSONArray("images");

			for (int x = 0; x < array_inages.length(); x++) {
				String photoItema=array_inages.getString(x);
				photos.add(photoItema);

			}


		} catch (JSONException e) {
			e.printStackTrace();
		}
		return details;
	}

	public int parseIfADInFavourite(String response, int statues){
		if (TextUtils.isEmpty(response)) {
			return statues;
		}

		return statues;
	}


	public int checkIfFollowComments(String response, int statues){
		if (TextUtils.isEmpty(response)) {
			return statues;
		}
		try {
            statues = Integer.parseInt(response);



		}finally {

        }
        return statues;
	}

	public void parseADInFavourite(String response){

		try {
			JSONObject jsonObject = new JSONObject(response);


		} catch (JSONException e) {
			e.printStackTrace();
		}

	}






}
