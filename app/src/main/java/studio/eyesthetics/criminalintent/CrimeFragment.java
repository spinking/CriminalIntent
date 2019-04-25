package studio.eyesthetics.criminalintent;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

public class CrimeFragment extends Fragment {
    public static final String EXTRA_CRIME_ID = "studio.eyesthetics.android.criminalintent.crime_id";
    private static final String DIALOG_DATE = "date";
    private static final String DIALOG_TIME = "time";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;
    private static final int REQUEST_CHOICE = 2;

    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private Button mTimeButton;
    private Button mChoiceButton;
    private CheckBox mSolvedCheckBox;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID crimeId = (UUID)getArguments().getSerializable(EXTRA_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
    }

    /*private void updateDate() {
        mDateButton.setText(DateFormat.getDateInstance(DateFormat.FULL).format(mCrime.getmDate()));
    }
    private void updateTime() {
        mTimeButton.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(mCrime.getmTime()));
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, parent, false);

        mTitleField = (EditText)v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getmTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setmTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mChoiceButton = (Button)v.findViewById(R.id.choice_button);
        updateDate();
        mChoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FragmentManager fm = getActivity().getSupportFragmentManager();
                //DataTimeChoiceFragment dialog = DataTimeChoiceFragment.newInstance(mCrime);
                //dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
                //dialog.show(fm, DIALOG_DATE);
                editDateTimeDialog();
            }
        });

        mSolvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setChecked(mCrime.isMsolved());
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setMsolved(isChecked);
            }
        });

        return v;
    }

    private void editDateTimeDialog() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        DataTimeChoiceFragment dialogFragment = new DataTimeChoiceFragment();
        dialogFragment.setTargetFragment(CrimeFragment.this, REQUEST_CHOICE);
        dialogFragment.show(fm, null);
    }

    public static CrimeFragment newInstance(UUID crimeId) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_CRIME_ID, crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);

        return  fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK) return;
        if(requestCode == REQUEST_DATE) {
            Date date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            //mCrime.setmDate(date);
            //mDateButton.setText(mCrime.getmDate().toString());
            combineDate(date);
            updateDate();
        }
        if(requestCode == REQUEST_TIME) {
            Date time = (Date)data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            //mCrime.setmTime(time);
            //mTimeButton.setText(mCrime.getmDate().toString());
            combineTime(time);
            updateDate();
            //updateTime();
        }
        if(requestCode == REQUEST_CHOICE) {
            int choice = data.getIntExtra(DataTimeChoiceFragment.EXTRA_CHOICE, 0);
            if(choice == 0) {
                return;
            }
            if(choice == DataTimeChoiceFragment.CHOICE_TIME) editTimeDialog();
            else if(choice == DataTimeChoiceFragment.CHOICE_DATE) editDateDialog();
        }
    }

    private void editDateDialog() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getmDate());
        dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);
        dialog.show(fm, null);
    }

    private void editTimeDialog() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        TimePickerFragment dialog = TimePickerFragment.newInstance(mCrime.getmDate());
        dialog.setTargetFragment(CrimeFragment.this, REQUEST_TIME);
        dialog.show(fm, null);
    }

    private void combineTime(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(mCrime.getmDate());
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(time);
        int hours = cal.get(Calendar.HOUR_OF_DAY);
        int mins = cal.get(Calendar.MINUTE);
        Date finalTime = new GregorianCalendar(year, month, day, hours, mins).getTime();
        mCrime.setmDate(finalTime);
    }

    private void combineDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(mCrime.getmDate());
        int hours = cal.get(Calendar.HOUR_OF_DAY);
        int mins = cal.get(Calendar.MINUTE);

        Date finalDate = new GregorianCalendar(year, month, day, hours, mins).getTime();
        mCrime.setmDate(finalDate);
    }

    private void updateDate() {
        mChoiceButton.setText(mCrime.getDateString());
    }
}
