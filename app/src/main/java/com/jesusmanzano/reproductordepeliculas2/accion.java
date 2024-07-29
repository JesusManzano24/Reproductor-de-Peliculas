package com.jesusmanzano.reproductordepeliculas2;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;

public class accion extends AppCompatActivity {
    VideoView videoView;
    Button Avengers;
    Button deadpool;
    Button Avengers1;
    Button IronMan;
    Button galaga;
    Button ungenio;
    Button yosoy;
    Button ultron;
    Button ultron2;
    Button Atrasar;
    Button Adelantar;
    Button play_pause;
    Button nuevaActividadButton;
    Button nuevaActividadButton2;
    TextView nombre;
    TextView usuarioyedad;
    private ImageView Foto;
    private boolean profilePhotoTaken = false;

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
        nuevaActividadButton2 = findViewById(R.id.Cambiarusuario);

        nombre = findViewById(R.id.nombre);
        usuarioyedad = findViewById(R.id.usuarioyedad);
        Foto = findViewById(R.id.imgusuario);

        // Recuperar y mostrar la foto de perfil si existe
        loadProfilePhoto();

        // Configurar los oyentes de clic para los botones de selección de video
        deadpool.setOnClickListener(v -> checkProfilePhotoAndLoadVideo(R.raw.deadpool, R.string.Deadpool));
        Avengers.setOnClickListener(v -> checkProfilePhotoAndLoadVideo(R.raw.avengers, "Wanda y Visión vs Midnigth y Corvus Glaive -"));
        Avengers1.setOnClickListener(v -> checkProfilePhotoAndLoadVideo(R.raw.avenger1, "Wanda y Visión vs Midnigth y Corvus Glaive Part2"));
        IronMan.setOnClickListener(v -> checkProfilePhotoAndLoadVideo(R.raw.nuevomark50, "Maw y Cull llegan a la Tierra  Nuevo Mark 50 "));
        galaga.setOnClickListener(v -> checkProfilePhotoAndLoadVideo(R.raw.galaga, "ese hombre esta jugando galaga"));
        ungenio.setOnClickListener(v -> checkProfilePhotoAndLoadVideo(R.raw.ungenio, "Un genio, millonario, playboy, filantropo.."));
        yosoy.setOnClickListener(v -> checkProfilePhotoAndLoadVideo(R.raw.yosoyironman, "Yo Soy Iron Man-Avengers Endgame"));
        ultron.setOnClickListener(v -> checkProfilePhotoAndLoadVideo(R.raw.avengersageofultron, "Batalla final en Sokovia-Avengers Age of Ultron"));
        ultron2.setOnClickListener(v -> checkProfilePhotoAndLoadVideo(R.raw.eradeultron, "Paz en Nuestros Tiempos"));

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

        // Llamar al método para mostrar el mensaje de bienvenida
        displayWelcomeMessage();

        // Configurar el Intent para el nuevo botón
        nuevaActividadButton.setOnClickListener(v -> {
            Intent intent = new Intent(accion.this, MainActivity4.class);
            startActivity(intent);
        });
        nuevaActividadButton2.setOnClickListener(v -> {
            Intent intent = new Intent(accion.this, MainActivity3.class);
            startActivity(intent);
        });
    }

    private void loadProfilePhoto() {
        try {
            FileInputStream fis = openFileInput("profile_photo.png");
            Bitmap photo = BitmapFactory.decodeStream(fis);
            Foto.setImageBitmap(photo);
            profilePhotoTaken = true;
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private void checkProfilePhotoAndLoadVideo(int videoResId, int infoResId) {
        if (!profilePhotoTaken) {
            showProfilePhotoDialog(() -> loadVideo(videoResId, getString(infoResId)));
        } else {
            loadVideo(videoResId, getString(infoResId));
        }
    }

    private void checkProfilePhotoAndLoadVideo(int videoResId, String info) {
        if (!profilePhotoTaken) {
            showProfilePhotoDialog(() -> loadVideo(videoResId, info));
        } else {
            loadVideo(videoResId, info);
        }
    }

    private void showProfilePhotoDialog(Runnable onPhotoTaken) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Para poder ver el video es necesario que pongas una foto de perfil.");
        builder.setPositiveButton("Aceptar", (dialog, which) -> takeProfilePhoto(onPhotoTaken));
        builder.setNegativeButton("Cancelar", (dialog, which) -> {
            Intent intent = new Intent(accion.this, MainActivity4.class);
            startActivity(intent);
            finish();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void takeProfilePhoto(Runnable onPhotoTaken) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            Foto.setImageBitmap(photo);
            profilePhotoTaken = true;
            try {
                FileOutputStream fos = openFileOutput("profile_photo.png", MODE_PRIVATE);
                photo.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Error al guardar la foto, intente de nuevo", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(accion.this, MainActivity4.class);
                startActivity(intent);
                finish();
            }
        } else {
            Intent intent = new Intent(accion.this, MainActivity4.class);
            startActivity(intent);
            finish();
        }
    }

    private void loadVideo(int videoResId, String info) {
        String videoPath = "android.resource://" + getPackageName() + "/" + videoResId;
        videoView.setVideoURI(Uri.parse(videoPath));
        videoView.start();
        nombre.setText(info);
    }
}

