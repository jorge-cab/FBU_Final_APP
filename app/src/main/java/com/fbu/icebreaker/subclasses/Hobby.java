package com.fbu.icebreaker.subclasses;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

import java.util.List;
import java.util.Objects;

@ParseClassName("Hobby")
public class Hobby extends ParseObject {

    private static final String KEY_NAME = "name";
    private static final String KEY_EMOJI = "emoji";
    private static final String KEY_IMAGE = "hobbyImage";
    private static final String KEY_DESCRIPTION = "hobbyDescription";
    private static final String KEY_TAGS = "hobbyTags";

    private boolean isChecked = false;

    public String getName() { return getString(KEY_NAME); }
    public void setName(String name) { put(KEY_NAME, name); }

    public String getEmoji() { return getString(KEY_EMOJI); }
    public void setEmoji(String emoji) { put(KEY_EMOJI, emoji); }

    public boolean getChecked() { return isChecked; }
    public void setChecked(boolean checked) { isChecked = checked; }

    public String getImage() { return Objects.requireNonNull(getParseFile("hobbyImage")).getUrl(); }
    public void setImage(ParseFile file) { put(KEY_IMAGE, file); }

    public String getDescription() { return getString(KEY_DESCRIPTION); }
    public void setDescription(String description) { put(KEY_DESCRIPTION, description); }

    public List<String> getTags() { return getList(KEY_TAGS); }
    public void setTags(List<String> tags) { put(KEY_TAGS, tags); }
}
