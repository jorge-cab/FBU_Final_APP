package com.fbu.icebreaker;

import android.app.Application;

import com.fbu.icebreaker.subclasses.ConversationStarter;
import com.fbu.icebreaker.subclasses.Hobby;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.facebook.ParseFacebookUtils;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Register Parse models here
        ParseObject.registerSubclass(Hobby.class);
        ParseObject.registerSubclass(ConversationStarter.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build());

        ParseFacebookUtils.initialize(this);
    }
}
