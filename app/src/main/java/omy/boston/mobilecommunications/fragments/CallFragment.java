package omy.boston.mobilecommunications.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import omy.boston.mobilecommunications.R;

public class CallFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    private EditText editTcall;
    private Button btnDial;
    private Button btnCall;
    private String telBroj;
    private static final String TEL_PREFIKS = "tel:";

    public CallFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_call, container, false);
        editTcall = (EditText) rootView.findViewById(R.id.editTcall);
        btnDial = (Button) rootView.findViewById(R.id.btnDial);
        btnDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasNumber()) {
                    telBroj = TEL_PREFIKS + editTcall.getText().toString();
                    Intent d = new Intent(Intent.ACTION_DIAL, Uri.parse(telBroj));
                    startActivity(d);
                }
            }
        });
        btnCall = (Button) rootView.findViewById(R.id.btnCall);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasNumber()){
                    telBroj = TEL_PREFIKS + editTcall.getText().toString();
                    Intent c = new Intent(Intent.ACTION_CALL, Uri.parse(telBroj));
                    startActivity(c);
                }
            }
        });
        return rootView;
    }
//------------------------------------ CODIRANO ----------------------------------------------------
    /**public void dial(View view){
        if (hasNumber()){
            telBroj = TEL_PREFIKS + editTcall.getText().toString();
            Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse(telBroj));
            startActivity(i);
        }
    }*/ //Premješteno u onCreateView

    private boolean hasNumber(){
        if (editTcall.getText().length()==0){
            Toast.makeText(getActivity(), "Ajd unesi broj...", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
//--------------------------------------------------------------------------------------------------
    public static CallFragment newInstance(int sectionNumber) {
        CallFragment fragment = new CallFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


}
