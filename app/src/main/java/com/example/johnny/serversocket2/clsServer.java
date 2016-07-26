package com.example.johnny.serversocket2;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Johnny on 21/07/2016.
 */
public class clsServer extends AsyncTask<Void, String, Boolean> {


    private Activity activity;
    private int puerto;


    Socket cliente = null;//Socket servicio que intermedia con los clientes
    boolean status = true;//Status de conectado o no conectado
    ServerSocket servidor = null; //Servidor
    DataInputStream flujoEntrada;
    String mensaje = "";
    int totalSolicitudes = 0;
    String mensajeError="";


    TextView txtPuerto;
    TextView txtUltimaSolicitud;
    TextView txtStatus;
    TextView txtTotalSolicitudes;

    public clsServer(Activity activity, int puerto, TextView txtUltimaSolicitud, TextView txtStatus, TextView txtTotalSolicitudes) {
        this.activity = activity;
        this.puerto = puerto;
        this.txtPuerto = txtPuerto;
        this.txtUltimaSolicitud = txtUltimaSolicitud;
        this.txtStatus = txtStatus;
        this.txtTotalSolicitudes = txtTotalSolicitudes;
    }




    /*Antes de iniciar el proceso, reiniciamos la barra de progreso*/
    @Override
    protected void onPreExecute() {
        this.txtStatus.setText("Conectado");
    }


    /*
    * El método doInBackground() se ejecuta en un hilo secundario (por tanto no podremos interactuar
    * con la interfaz), pero sin embargo todos los demás se ejecutan en el hilo principal, lo que
    * quiere decir que dentro de ellos podremos hacer referencia directa a nuestros controles de
    * usuario para actualizar la interfaz. Ademas si se llama al metodo publishProgress() este
    * automáticamente ejecuta el onProgressUpdate() que actualizara la interfaz si es necesario
    * */
    @Override
    protected Boolean doInBackground(Void... params) {

        try {
            servidor = new ServerSocket(puerto);
            //System.out.println("Server: ONLINE, escuchando por el puerto " + puerto);

            while (status) {
                //System.out.println("Server: Esperando solicitud.....");
                cliente = servidor.accept();
                //System.out.println("Server: Se recibio solicitud");
                totalSolicitudes++;
                flujoEntrada = new DataInputStream(cliente.getInputStream());
                mensaje = flujoEntrada.readUTF();
                //System.out.println("El dato recibido es " + flujoEntrada.readUTF());//Se interpreta el dato que mando el server

                publishProgress(totalSolicitudes+"",mensaje);

            }


               /*Se le retorna true a la funcion onPostExecute*/
            return true;
        } catch (IOException ex) {
            mensajeError= ex.getMessage();
            return false;
        }

    }

    @Override
    protected void onProgressUpdate(String... values) {
        txtTotalSolicitudes.setText(values[0]);
        txtUltimaSolicitud.setText(values[1]);
    }


    @Override
    protected void onPostExecute(Boolean result) {
        /*Tan pronto termine el proceso, se muestra un toast en la activity indincando que termino
        * el proceso*/
        if(result) {
            Toast.makeText(activity, "Servidor desconectado",
                    Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(activity, "Error iniciando el servidor "+mensajeError,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCancelled() {
        /*Si se cancela el proceso se le indica al usuario*/
        Toast.makeText(activity, "Tarea cancelada!",
                Toast.LENGTH_SHORT).show();
    }

}
