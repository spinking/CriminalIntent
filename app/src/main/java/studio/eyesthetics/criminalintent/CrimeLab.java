package studio.eyesthetics.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

public class CrimeLab {
    private ArrayList<Crime> mCrimes;

    private static CrimeLab sCrimeLab;
    private Context mAppContext;

    private CrimeLab(Context AppContext) {
        mAppContext = AppContext;
        mCrimes = new ArrayList<Crime>();
        for(int i = 0; i < 100; i++) {
            Crime c = new Crime();
            c.setmTitle("Crime #" + i);
            c.setMsolved(i % 2 == 0);
            mCrimes.add(c);
        }
    }

    public static CrimeLab get(Context c) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(c.getApplicationContext());
        }
        return sCrimeLab;
    }

    public ArrayList<Crime> getCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID id) {
        for(Crime c : mCrimes) {
            if(c.getmId().equals(id)) return c;
        }
        return null;
    }
}
