package com.example.icebreaker.Subclasses;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Hobby")
public class Hobby extends ParseObject {

    public static final String KEY_NAME = "name";
    public static final String KEY_EMOJI = "emoji";

    public String getName() { return getString(KEY_NAME); }
    public void setName(String name) { put(KEY_NAME, name); }

    public String getEmoji() { return getString(KEY_EMOJI); }
    public void setEmoji(String emoji) { put(KEY_EMOJI, emoji); }
}
