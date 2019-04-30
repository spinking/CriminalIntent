package studio.eyesthetics.criminalintent;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;

public class Crime {
    private static final String JSON_ID = "id";
    private static final String JSON_TITLE = "title";
    private static final String JSON_SOLVED = "solved";
    private static final String JSON_DATE = "date";
    private static final String JSON_PHOTO = "photo";

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private Photo mPhoto;
    //private Date mTime;
    private boolean msolved;

    public Crime() {
        mId = UUID.randomUUID();
        mDate = new Date();
        //mTime = new Date();
    }

    public Crime(JSONObject json) throws JSONException {
        mId = UUID.fromString(json.getString(JSON_ID));
        if(json.has(JSON_TITLE)) {
            mTitle = json.getString(JSON_TITLE);
        }
        msolved = json.getBoolean(JSON_SOLVED);
        mDate = new Date((json.getLong(JSON_DATE)));
        if(json.has(JSON_PHOTO)) {
            mPhoto = new Photo(json.getJSONObject(JSON_PHOTO));
        }
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_ID, mId.toString());
        json.put(JSON_TITLE, mTitle);
        json.put(JSON_SOLVED, msolved);
        json.put(JSON_DATE, mDate.getTime());
        if(mPhoto != null) {
            json.put(JSON_PHOTO, mPhoto.toJSON());
        }
        return json;
    }

    @Override
    public String toString() {
        return mTitle;
    }

    public UUID getmId() {
        return mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

/*    public Date getmTime() {
        return mTime;
    }

    public void setmTime(Date mTime) {
        this.mTime = mTime;
    }*/

    public boolean isMsolved() {
        return msolved;
    }

    public void setMsolved(boolean msolved) {
        this.msolved = msolved;
    }

    public String getDateString() {
        return DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.SHORT).format(mDate);
    }

    public Photo getPhoto() {
        return mPhoto;
    }

    public void setPhoto(Photo p) {
        mPhoto = p;
    }
}
