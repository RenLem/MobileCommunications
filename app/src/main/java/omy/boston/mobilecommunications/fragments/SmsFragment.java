package omy.boston.mobilecommunications.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import omy.boston.mobilecommunications.R;

public class SmsFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String TEL_PREFIKS = "tel:";

    private EditText edNumber;
    private EditText edMessage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sms, container, false);
        edMessage = (EditText) rootView.findViewById(R.id.edMessage);
        edNumber = (EditText) rootView.findViewById(R.id.edNumber);

        Button btnByAp = (Button) rootView.findViewById(R.id.btnByAp);
        btnByAp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasNumber() && hasTekst()) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.putExtra("address", edNumber.getText().toString());
                    i.putExtra("sms_body", edMessage.getText().toString());
                    i.setType("vnd.android-dir/mms-sms");
                    startActivity(i);
                }
            }
        });
        Button btnDirect = (Button) rootView.findViewById(R.id.btnDirect);
        btnDirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(edNumber.getText().toString(), null, edMessage.getText().toString(), null, null);

                Toast.makeText(getActivity(), edNumber + "\n" + edMessage, Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    public SmsFragment() {
    }

    /**
     * public void prepareSMS(){
     * if (hasNumber() && hasTekst()){
     * Intent poruka = new Intent(Intent.ACTION_VIEW);
     * poruka.putExtra("address", editTel.getText().toString());
     * poruka.putExtra("sms_body", editTekst.getText().toString());
     * poruka.setType("vnd.android-dit/mes-sms");
     * startActivity(poruka);
     * }
     * }


    public void sendSMS(){
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(edNumber.getText().toString(), null, edMessage.getText().toString(), null, null);
    }*/ //Premješteno u metodu onCreateView

    public static SmsFragment newInstance(int sectionNumber) {
        SmsFragment fragment = new SmsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    private boolean hasTekst() {
        if (edMessage.getText().length() == 0) {
            Toast.makeText(getContext(), "Ajd unesi neki tekst..", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean hasNumber() {
        if (edNumber.getText().length() == 0) {
            Toast.makeText(getContext(), "Ajd neki unesi tel...", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }
}
