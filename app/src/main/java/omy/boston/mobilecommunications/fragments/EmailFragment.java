package omy.boston.mobilecommunications.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import omy.boston.mobilecommunications.R;

/**
 * Created by LosFrancisco on 02-Feb-17.
 */

public class EmailFragment extends Fragment {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private EditText edEmail;
    private EditText edSubject;
    private EditText edMessage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_email, container, false);
        edEmail = (EditText) rootView.findViewById(R.id.editAddres);
        edSubject = (EditText) rootView.findViewById(R.id.editSubject);
        edMessage = (EditText) rootView.findViewById(R.id.editMessage);

        Button btnSend = (Button) rootView.findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputOk()){
                    sendEmail(edEmail.getText().toString(),
                            edSubject.getText().toString(),
                            edMessage.getText().toString());
                }
            }
        });

        return rootView;
    }

    public EmailFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static EmailFragment newInstance(int sectionNumber) {
        EmailFragment fragment = new EmailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    private boolean inputOk(){
        if (edEmail.getText().length()==0
                || edSubject.getText().length()==0
                || edMessage.getText().length()==0){
            Toast.makeText(getActivity(), "Please fill all data!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void sendEmail(String address, String subject, String message){
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{address});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(emailIntent, "Please select application"));
    }

}


