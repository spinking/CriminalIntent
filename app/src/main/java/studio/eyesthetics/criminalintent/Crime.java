package studio.eyesthetics.criminalintent;

import java.util.Date;
import java.util.UUID;

public class Crime {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private Date mTime;
    private boolean msolved;

    public Crime() {
        mId = UUID.randomUUID();
        mDate = new Date();
        mTime = new Date();
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

    public Date getmTime() {
        return mTime;
    }

    public void setmTime(Date mTime) {
        this.mTime = mTime;
    }

    public boolean isMsolved() {
        return msolved;
    }

    public void setMsolved(boolean msolved) {
        this.msolved = msolved;
    }
}
