package omy.boston.mobilecommunications.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import omy.boston.mobilecommunications.R;

/**
 * Created by LosFrancisco on 01-Feb-17.
 */

public class NetworkFragment extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private Button btnNetCheck;
    private Button btnTNSC;
    private Button btnEnabWiFi;
    private Button btnScWiFi;
    private Button btnWiFiInfo;
    private Button btnAddWiFiNet;

    public NetworkFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_network, container, false);

        btnNetCheck = (Button) rootView.findViewById(R.id.btnNetCheck);
        btnNetCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dostupnostInternetVeze();
            }
        });
        btnTNSC = (Button) rootView.findViewById(R.id.btnTNSC);
        btnTNSC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                precenjeStanjaMreze();
            }
        });
        btnEnabWiFi = (Button) rootView.findViewById(R.id.btnEnabWiFi);
        btnEnabWiFi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                omoguciWiFi();
            }
        });
        btnScWiFi = (Button) rootView.findViewById(R.id.btnScWiFi);
        btnScWiFi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skeniranjeWiFiMreze();
            }
        });
        btnWiFiInfo = (Button) rootView.findViewById(R.id.btnWiFiInfo);
        btnWiFiInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoWiFi();
            }
        });
        btnAddWiFiNet = (Button) rootView.findViewById(R.id.btnAddWiFiNet);
        btnAddWiFiNet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dodajBezicnu();
            }
        });
        return rootView;
    }

    public void dostupnostInternetVeze(){
        // upravitelj mreže
        ConnectivityManager mobilnaMreza = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        // dohvačanje aktivne mreže
        NetworkInfo aktivnaMreza = mobilnaMreza.getActiveNetworkInfo();
        if (aktivnaMreza != null && aktivnaMreza.getState()== NetworkInfo.State.CONNECTED){
            // provjera vrste aktivne mreže
            int vrstaMreze = aktivnaMreza.getType();
            switch (vrstaMreze){
                case (ConnectivityManager.TYPE_MOBILE):
                    Toast.makeText(getActivity(),
                            "Aktivna je mobilna mreža", Toast.LENGTH_SHORT).show();
                    if (aktivnaMreza.isRoaming()){
                        // obrada podataka o roaming mreži
                        Toast.makeText(getActivity(),
                                "Aktivna je roming mreža", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case (ConnectivityManager.TYPE_WIFI):
                    Toast.makeText(getActivity(),
                            "Aktivna je bežićna mreža", Toast.LENGTH_SHORT).show();
                    break;
            }
        }else {
            // Prikaz poruke o nedostupnosti mreže
            Toast.makeText(getActivity(), "Internet veza nedostupna", Toast.LENGTH_SHORT).show();
        }
        }

    private void precenjeStanjaMreze(){
        getActivity().registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle paket = intent.getExtras();
                String msg = " ";
                if (paket.getBoolean(ConnectivityManager.EXTRA_IS_FAILOVER)){
                     msg = "Problemi na mreži " + "EXTRA_IS_FAILOVER";
                }
                if (paket.getBoolean(ConnectivityManager.EXTRA_NO_CONNECTIVITY)){
                    msg = "Naprava nije spojena niti na jednu mrežu " + "EXTRA_NO_CONNECTIVITY";
                }
                if (paket.getString(ConnectivityManager.EXTRA_REASON) != null){
                    msg = "Problemi sa mrežom " + "EXTRA_REASON";
                }

                if(msg.length()>0){
                    Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                }
            }
        }, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    private void omoguciWiFi(){
        WifiManager bezicnaMreza = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if (!bezicnaMreza.isWifiEnabled()){
            if (bezicnaMreza.getWifiState() != WifiManager.WIFI_STATE_ENABLING){
                bezicnaMreza.setWifiEnabled(true);
            }
        }
    }

    private void skeniranjeWiFiMreze(){
        final WifiManager bezicnaMreza = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        // registracija na dohvaćanje mreža
        getActivity().registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                List<ScanResult> skeniraneMreze = bezicnaMreza.getScanResults();
                for (int i = 0; i<skeniraneMreze.size(); i++){
                    ScanResult mreza = skeniraneMreze.get(i);
                    String nazivMreze = mreza.SSID;
                    int jacinaSignala = mreza.level;
                }
            }
        }, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        // pokretanje skeniranja
        bezicnaMreza.startScan();
    }

    private void infoWiFi(){
        WifiManager bezicnaMreza = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = bezicnaMreza.getConnectionInfo();

        if (info.getBSSID() != null){
            String nazivMreze = info.getSSID();
            int brzinaMreze = info.getLinkSpeed();
            int jacinaSignala = WifiManager.calculateSignalLevel(info.getRssi(), 5);
        }
    }

    private void dodajBezicnu(){
        WifiManager bezicnaMreza = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiConfiguration wfc = new WifiConfiguration();
        wfc.SSID = "Naziv merže";
        wfc.preSharedKey = "WPA ključ";
        bezicnaMreza.addNetwork(wfc);
        bezicnaMreza.saveConfiguration();
    }

    public static NetworkFragment newInstance(int sectionNumber) {
        NetworkFragment fragment = new NetworkFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


}

