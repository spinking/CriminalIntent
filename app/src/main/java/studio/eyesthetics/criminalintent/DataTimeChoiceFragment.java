package studio.eyesthetics.criminalintent;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;

import java.text.DateFormat;

public class DataTimeChoiceFragment extends DialogFragment {

    private Button mDateButton;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v = getActivity().getLayoutInflater().inflate(R.layout.data_time_choice, null);
        mDateButton = (Button)v.findViewById(R.id.data_picker_choice);
        //mDateButton.setText(DateFormat.getDateInstance(DateFormat.FULL).format(mCrime.getmDate()));

        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v)
                .setPositiveButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return builder.create();
    }
}
