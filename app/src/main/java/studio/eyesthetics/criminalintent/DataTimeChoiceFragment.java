package studio.eyesthetics.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;


public class DataTimeChoiceFragment extends DialogFragment {
    public static final String EXTRA_CHOICE = "studio.eyesthetics.criminalintent.choice";

    private int mChoice = 0;

    public static final int CHOICE_DATE = 1;
    public static final int CHOICE_TIME = 2;

    private Button mDateButton;
    private Button mTimeButton;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View v = getActivity().getLayoutInflater()
                .inflate(R.layout.data_time_choice, null);

        mDateButton = (Button)v.findViewById(R.id.data_picker_choice);
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChoice = CHOICE_DATE;
                sendResult(Activity.RESULT_OK);
            }
        });

        mTimeButton = (Button)v.findViewById(R.id.time_picker_choice);
        mTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChoice = CHOICE_TIME;
                sendResult(Activity.RESULT_OK);
            }
        });

        builder.setView(v).setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return builder.create();
    }

    private void sendResult(int resultCode) {
        if (getTargetFragment() == null) return;
        Intent i = new Intent();
        i.putExtra(EXTRA_CHOICE, mChoice);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
    }
}