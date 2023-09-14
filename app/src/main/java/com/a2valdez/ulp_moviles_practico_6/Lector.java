package com.a2valdez.ulp_moviles_practico_6;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.Telephony;
import android.util.Log;

public class Lector extends Service {

    private Thread hilo;
    private Boolean bandera = true;
    public Lector() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        leerMensajes();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bandera = false;
    }

    public void leerMensajes(){
        Uri mensajes = Uri.parse("content://sms/inbox");
        ContentResolver cr = this.getContentResolver();

        hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (bandera){
                        Cursor cursor = cr.query(mensajes, null, null, null, "date DESC LIMIT 5" );
                        String fecha;
                        String mensaje;
                        String contacto;
                        if(cursor.getCount() > 0){
                            while(cursor.moveToNext()){
                                int colFecha = cursor.getColumnIndex(Telephony.Sms.DATE);
                                int colMensaje = cursor.getColumnIndex(Telephony.Sms.BODY);
                                int colContacto = cursor.getColumnIndex(Telephony.Sms.ADDRESS);
                                fecha = cursor.getString(colFecha);
                                mensaje = cursor.getString(colMensaje);
                                contacto = cursor.getString(colContacto);
                                Log.d("salida", "Fecha: "+mensaje+" Contacto: "+contacto+" Mensaje: "+mensaje);
                            }
                        }
                        Thread.sleep(9000);
                    }
                } catch (Exception ex){
                    Log.d("salida", "Error en el run del hilo "+ex.getMessage());
                }
            }
        });
        hilo.start();
    }
}