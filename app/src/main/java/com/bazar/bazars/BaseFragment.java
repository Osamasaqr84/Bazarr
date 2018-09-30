package com.bazar.bazars;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View.OnClickListener;

import javax.xml.transform.ErrorListener;

/**
 * @author Hardik A Bhalodi
 */
abstract public class BaseFragment extends Fragment implements
		OnClickListener, ErrorListener {
	DetailsActivity activity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
 		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		activity = (DetailsActivity) getActivity();

	}

	protected abstract boolean isValidate();

	public boolean OnBackPressed() {
		// activity.removeAllFragment(new UberMainFragment(), false,
		// Const.FRAGMENT_MAIN);

		activity.onBackPressed();
		//activity.startActivity(new Intent(activity, Main2Activity.class));
		//activity.finish();
		return true;
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onResume()
	 */
	@Override
	public void onResume() {
		// TODO Auto-generated method stub

		super.onResume();
		//activity.actionBar.show();

	}

}
