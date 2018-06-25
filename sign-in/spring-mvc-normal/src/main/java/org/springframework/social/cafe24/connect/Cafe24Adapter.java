package org.springframework.social.cafe24.connect;

import org.springframework.social.cafe24.api.Cafe24;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

public class Cafe24Adapter implements ApiAdapter<Cafe24> {

	@Override
	public boolean test(Cafe24 api) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setConnectionValues(Cafe24 cafe24, ConnectionValues values) {
		// TODO Auto-generated method stub

		
	}

	@Override
	public UserProfile fetchUserProfile(Cafe24 api) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateStatus(Cafe24 api, String message) {
		// TODO Auto-generated method stub
		
	}

}
