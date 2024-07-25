package com.jesusmanzano.reproductordepeliculas2;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;

public class accion extends AppCompatActivity {
    VideoView videoView;
    Button Avengers;
    Button deadpool;
    Button Avengers1;
    Button IronMan;;
    Button galaga;
    Button ungenio;
    Button yosoy;
    Button ultron;
    Button ultron2;
    Button Atrasar;
    Button Adelantar;
    Button play_pause;
    Button nuevaActividadButton; // Nuevo botón
    TextView nombre;
    TextView usuarioyedad;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accion);

        videoView = findViewById(R.id.video2);
        deadpool = findViewById(R.id.b1);
        Avengers = findViewById(R.id.b2);
        Avengers1 = findViewById(R.id.b3);
        IronMan = findViewById(R.id.b4);
        galaga = findViewById(R.id.b5);
        ungenio = findViewById(R.id.b6);
        yosoy = findViewById(R.id.b7);
        ultron = findViewById(R.id.b8);
        ultron2 = findViewById(R.id.b9);
        Atrasar = findViewById(R.id.Atrasar);
        Adelantar = findViewById(R.id.Adelantar);
        play_pause = findViewById(R.id.play_pause);
        nuevaActividadButton = findViewById(R.id.Atras);

        nombre = findViewById(R.id.nombre);
        usuarioyedad = findViewById(R.id.usuarioyedad);

        // Carga el archivo de video inicial
        String initialVideoPath = "android.resource://" + getPackageName() + "/" + R.raw.deadpool;
        videoView.setVideoURI(Uri.parse(initialVideoPath));

        play_pause.setOnClickListener(v -> {
            if (videoView.isPlaying()) {
                videoView.pause();
                Toast.makeText(accion.this, "Pausa", Toast.LENGTH_SHORT).show();
            } else {
                videoView.start();
                Toast.makeText(accion.this, "Play", Toast.LENGTH_SHORT).show();
            }
        });

        Atrasar.setOnClickListener(v -> {
            int currentPosition = videoView.getCurrentPosition();
            if (videoView.isPlaying()) {
                videoView.seekTo(Math.max(0, currentPosition - 5000));
                Toast.makeText(accion.this, "Video atrasado 5 segundos", Toast.LENGTH_SHORT).show();
            }
        });

        Adelantar.setOnClickListener(v -> {
            int currentPosition = videoView.getCurrentPosition();
            if (videoView.isPlaying()) {
                videoView.seekTo(currentPosition + 5000);
                Toast.makeText(accion.this, "Video adelantado 5 segundos", Toast.LENGTH_SHORT).show();
            }
        });

        deadpool.setOnClickListener(v -> {
            videoView.setVideoURI(Uri.parse(initialVideoPath));
            videoView.start();
            nombre.setText(R.string.Deadpool);
        });

        Avengers.setOnClickListener(v -> {
            String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.avengers;
            videoView.setVideoURI(Uri.parse(videoPath));
            videoView.start();
            String info = "Wanda y Visión vs Midnigth y Corvus Glaive -";
            nombre.setText(info);
        });

        Avengers1.setOnClickListener(v -> {
            String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.avenger1;
            videoView.setVideoURI(Uri.parse(videoPath));
            videoView.start();
            String info = "Wanda y Visión vs Midnigth y Corvus Glaive Part2";
            nombre.setText(info);
        });

        IronMan.setOnClickListener(v -> {
            String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.nuevomark50;
            videoView.setVideoURI(Uri.parse(videoPath));
            videoView.start();
            String info = "Maw y Cull llegan a la Tierra  Nuevo Mark 50 ";
            nombre.setText(info);
        });

        galaga.setOnClickListener(v -> {
            String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.galaga;
            videoView.setVideoURI(Uri.parse(videoPath));
            videoView.start();
            String info = "ese hombre esta jugando galaga";
            nombre.setText(info);
        });

        ungenio.setOnClickListener(v -> {
            String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.ungenio;
            videoView.setVideoURI(Uri.parse(videoPath));
            videoView.start();
            String info = "Un genio,millonario, playboy, filantropo..";nombre.setText(info);
        });

        yosoy.setOnClickListener(v -> {
            String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.yosoyironman;
            videoView.setVideoURI(Uri.parse(videoPath));
            videoView.start();
            String info = "Yo Soy Iron Man-Avengers Endgame";
            nombre.setText(info);
        });

        ultron.setOnClickListener(v -> {
            String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.avengersageofultron;
            videoView.setVideoURI(Uri.parse(videoPath));
            videoView.start();
            String info = "Batalla final en Sokovia-Avengers Age of Ultron";
            nombre.setText(info);
        });

        ultron2.setOnClickListener(v -> {
            String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.eradeultron;
            videoView.setVideoURI(Uri.parse(videoPath));
            videoView.start();
            String info = "Vegeta Destruye la máquina de fuerza :(";
            nombre.setText(info);
        });

        // Llamar al método para mostrar el mensaje de bienvenida
        displayWelcomeMessage();

        // Configurar el Intent para el nuevo botón
        nuevaActividadButton.setOnClickListener(v -> {
            Intent intent = new Intent(accion.this, MainActivity4.class);
            startActivity(intent);
        });
    }

    private void displayWelcomeMessage() {
        String nombreArchivo = "UserData.txt";
        String contenidoArchivo = "";
        String nombre = "";
        String edad = "";

        try {
            BufferedReader buffered = new BufferedReader(new InputStreamReader(openFileInput(nombreArchivo)));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = buffered.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            contenidoArchivo = stringBuilder.toString();
            buffered.close();
        } catch (Exception ex) {
            Log.e("Ficheros", "Error al leer el archivo " + ex);
        }

        // Extraer el nombre y la edad del usuario
        for (String line : contenidoArchivo.split("\n")) {
            if (line.startsWith("nombre:")) {
                nombre = line.split(":")[1].trim();
            } else if (line.startsWith("Edad:")) {
                edad = line.split(":")[1].trim();
            }
        }

        // Mostrar el mensaje de bienvenida
        usuarioyedad.setText("Nombre: " + nombre + "\nEdad: " + edad);
    }

}
