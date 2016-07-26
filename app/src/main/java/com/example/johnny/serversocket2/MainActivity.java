package com.example.johnny.serversocket2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView txtPuerto;
    TextView txtUltimaSolicitud;
    TextView txtStatus;
    TextView txtTotalSolicitudes;
    clsServer server;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtPuerto = (TextView) findViewById(R.id.txtPuerto);
        txtUltimaSolicitud = (TextView) findViewById(R.id.txtUltimaSolicitud);
        txtStatus = (TextView) findViewById(R.id.txtStatus);
        txtTotalSolicitudes = (TextView) findViewById(R.id.txtTotalSolicitudes);

    }


    public void conectar(View view){
        int puerto = Integer.parseInt(txtPuerto.getText().toString());
        server= new clsServer(this, puerto,txtUltimaSolicitud,txtStatus,txtTotalSolicitudes);
        server.execute();
    }


    public void cancelar(View view){
        server.cancel(true);
    }
}
