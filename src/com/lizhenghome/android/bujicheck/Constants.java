package com.lizhenghome.android.bujicheck;

public interface Constants {
	int BUJI_STATUS_SAFE = 0;
	int BUJI_STATUS_DANGER = 1;
	
	String PARAM_BUJI_STATUS = "bujiStatus";
	String PARAM_PHONE_NUMBER = "phoneNumber";
	String PARAM_LOCATION = "position";
	
	//String BUJI_SEND_URL = "http://bujicheck.appspot.com/bujisend";
	String BUJI_SEND_URL = "http://192.168.100.5:8888/bujisend";
	//String BUJI_CHECK_URL = "http://bujicheck.appspot.com/bujicheck";
	String BUJI_CHECK_URL = "http://192.168.100.5:8888/bujicheck";
}
