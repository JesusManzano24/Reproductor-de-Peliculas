package com.jesusmanzano.reproductordepeliculas2;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class caricaturas extends AppCompatActivity {
    VideoView videoView;
    Button AcceleRacers;
    Button AcceleRacers1;
    Button Battleforce5;
    Button Battleforce51;
    Button johnnytest;
    Button jonytest1;
    Button Jovenestitanes;
    Button thundercats;
    Button dbz;
    Button Atrasar;
    Button Adelantar;
    Button play_pause;
    Button nuevaActividadButton; // Nuevo botón
    TextView nombre;
    TextView usuarioyedad;
    Button Atras;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_PERMISSION = 2;
    private String profilePhotoPath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caricaturas);

        videoView = findViewById(R.id.video1);
        AcceleRacers = findViewById(R.id.AcceleRacers);
        AcceleRacers1 = findViewById(R.id.AcceleRacers1);
        Battleforce5 = findViewById(R.id.Battleforce5);
        Battleforce51 = findViewById(R.id.battleforce51);
        johnnytest = findViewById(R.id.johnnytest);
        jonytest1 = findViewById(R.id.johnnytest1);
        Jovenestitanes = findViewById(R.id.losjovenestitanes);
        thundercats = findViewById(R.id.thundercats);
        dbz = findViewById(R.id.dbz);
        Atrasar = findViewById(R.id.Atrasar);
        Adelantar = findViewById(R.id.Adelantar);
        play_pause = findViewById(R.id.play_pause);
        Atras = findViewById(R.id.Atras);

        nombre = findViewById(R.id.nombre);
        usuarioyedad = findViewById(R.id.usuarioyedad);

        // Carga el archivo de video inicial
        String initialVideoPath = "android.resource://" + getPackageName() + "/" + R.raw.acelera;
        videoView.setVideoURI(Uri.parse(initialVideoPath));

        play_pause.setOnClickListener(v -> {
            if (videoView.isPlaying()) {
                videoView.pause();
                Toast.makeText(caricaturas.this, "Pausa", Toast.LENGTH_SHORT).show();
            } else {
                videoView.start();
                Toast.makeText(caricaturas.this, "Play", Toast.LENGTH_SHORT).show();
            }
        });

        Atrasar.setOnClickListener(v -> {
            int currentPosition = videoView.getCurrentPosition();
            if (videoView.isPlaying()) {
                videoView.seekTo(Math.max(0, currentPosition - 5000));
                Toast.makeText(caricaturas.this, "Video atrasado 5 segundos", Toast.LENGTH_SHORT).show();
            }
        });

        Adelantar.setOnClickListener(v -> {
            int currentPosition = videoView.getCurrentPosition();
            if (videoView.isPlaying()) {
                videoView.seekTo(currentPosition + 5000);
                Toast.makeText(caricaturas.this, "Video adelantado 5 segundos", Toast.LENGTH_SHORT).show();
            }
        });

        AcceleRacers.setOnClickListener(v -> checkProfilePhoto(() -> {
            videoView.setVideoURI(Uri.parse(initialVideoPath));
            videoView.start();
            nombre.setText(R.string.AcceleRacers);
        }));

        AcceleRacers1.setOnClickListener(v -> checkProfilePhoto(() -> {
            String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.go;
            videoView.setVideoURI(Uri.parse(videoPath));
            videoView.start();
            String info = "Go AcceleRacers";
            nombre.setText(info);
        }));

        Battleforce5.setOnClickListener(v -> checkProfilePhoto(() -> {
            String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.bf5curiosidades;
            videoView.setVideoURI(Uri.parse(videoPath));
            videoView.start();
            String info = "Battle Force 5 Curiosidades";
            nombre.setText(info);
        }));

        Battleforce51.setOnClickListener(v -> checkProfilePhoto(() -> {
            String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.drive_to_survive;
            videoView.setVideoURI(Uri.parse(videoPath));
            videoView.start();
            String info = "Localizado Y Perdido";
            nombre.setText(info);
        }));

        johnnytest.setOnClickListener(v -> checkProfilePhoto(() -> {
            String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.regresoajohnny_mon;
            videoView.setVideoURI(Uri.parse(videoPath));
            videoView.start();
            String info = "Regreso a Johnny-mon";
            nombre.setText(info);
        }));

        jonytest1.setOnClickListener(v -> checkProfilePhoto(() -> {
            String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.johnny_el_lanzallamas;
            videoView.setVideoURI(Uri.parse(videoPath));
            videoView.start();
            String info = "johnny el Lanzallamas";
            nombre.setText(info);
        }));

        Jovenestitanes.setOnClickListener(v -> checkProfilePhoto(() -> {
            String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.jovenes;
            videoView.setVideoURI(Uri.parse(videoPath));
            videoView.start();
            String info = "Se Acabo La Cita";
            nombre.setText(info);
        }));

        thundercats.setOnClickListener(v -> checkProfilePhoto(() -> {
            String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.thundercats_opening;
            videoView.setVideoURI(Uri.parse(videoPath));
            videoView.start();
            String info = "thundercats Opening";
            nombre.setText(info);
        }));

        dbz.setOnClickListener(v -> checkProfilePhoto(() -> {
            String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.dragon_ball_z;
            videoView.setVideoURI(Uri.parse(videoPath));
            videoView.start();
            String info = "Vegeta Destruye la máquina de fuerza :(";
            nombre.setText(info);
        }));

        // Llamar al método para mostrar el mensaje de bienvenida
        displayWelcomeMessage();

        // Configurar el Intent para el nuevo botón
        nuevaActividadButton.setOnClickListener(v -> {
            Intent intent = new Intent(caricaturas.this, MainActivity3.class);
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

    private void checkProfilePhoto(Runnable onSuccess) {
        if (profilePhotoPath == null) {
            new AlertDialog.Builder(this)
                    .setTitle("Foto de Perfil")
                    .setMessage("Debes de tomar una foto de perfil antes de reproducir.")
                    .setPositiveButton("Aceptar", (dialog, which) -> takeProfilePhoto())
                    .setNegativeButton("Cancelar", (dialog, which) -> {
                        // Regresar al menú principal
                        Intent intent = new Intent(caricaturas.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    })
                    .show();
        } else {
            onSuccess.run();
        }
    }

    private void takeProfilePhoto() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
        } else {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            // Guardar la imagen en el almacenamiento
            profilePhotoPath = saveImageToExternalStorage(imageBitmap);
            Toast.makeText(this, "Foto de perfil guardada", Toast.LENGTH_SHORT).show();
        }
    }

    private String saveImageToExternalStorage(Bitmap bitmap) {
        String fileName = "profile_photo.jpg";
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName);
        try (FileOutputStream out = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            return file.getAbsolutePath();
        } catch (IOException e) {
            Log.e("SaveImage", "Error al guardar la imagen", e);
            return null;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takeProfilePhoto();
            } else {
                Toast.makeText(this, "Permisos de cámara y almacenamiento denegados", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
