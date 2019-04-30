package studio.eyesthetics.criminalintent;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

public class CriminalIntentJSONSerializer {
    private Context mContext;
    private String mFileName;
    private static final String TAG = "FileSave";

    public CriminalIntentJSONSerializer(Context c, String f) {
        mContext = c;
        mFileName = f;
    }

    public ArrayList<Crime> loadCrimes() throws IOException, JSONException {
        ArrayList<Crime> crimes = new ArrayList<Crime>();
        BufferedReader reader = null;

        Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);

        File extCrimeFile = new File(mContext.getExternalFilesDir(null), mFileName);

        try {
            if(isSDPresent && extCrimeFile.exists()) {
                Log.d(TAG, "File load from external storage");
                FileInputStream extFileInputStream = new FileInputStream(extCrimeFile);
                reader = new BufferedReader(new InputStreamReader(extFileInputStream));
            } else {
                Log.d(TAG, "File load from internal storage");
                InputStream in = mContext.openFileInput(mFileName);
                reader = new BufferedReader(new InputStreamReader(in));
            }

            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();
            for(int i = 0; i < array.length(); i++) {
                crimes.add(new Crime(array.getJSONObject(i)));
            }
        } catch (FileNotFoundException e) {

        } finally {
            if(reader != null) {
                reader.close();
            }
            return crimes;
        }
    }

    public void savedCrimes(ArrayList<Crime> crimes)
        throws JSONException, IOException {
        JSONArray array = new JSONArray();
        Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);

        for(Crime c : crimes) {
            array.put(c.toJSON());
        }

        Writer writer = null;
        try {
            if(isSDPresent) {
                Log.d(TAG, "File saved in external storage");
                File extDataDir = new File(mContext.getExternalFilesDir(null), mFileName);
                File extCrimeFile = new File(extDataDir.toString());
                FileOutputStream extFOS = new FileOutputStream(extCrimeFile);
                writer = new OutputStreamWriter(extFOS);
            } else {
                Log.d(TAG, "File saved in internal storage");
                OutputStream out = mContext.openFileOutput(mFileName, Context.MODE_PRIVATE);
                writer = new OutputStreamWriter(out);
            }

            writer.write(array.toString());
        } finally {
            if(writer != null) {
                writer.close();
            }
        }
    }
}
