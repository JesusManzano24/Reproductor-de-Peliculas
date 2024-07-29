package com.jesusmanzano.reproductordepeliculas2;

import android.Manifest;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

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
import androidx.lifecycle.LifecycleOwner;

import com.google.common.util.concurrent.ListenableFuture;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
    Button Atrasar;
    Button Adelantar;
    Button play_pause;
    Button nuevaActividadButton;
    Button nuevaActividadButton2;
    TextView nombre;
    TextView usuarioyedad;
    Button Atras;
    private ImageView Foto;
    private boolean profilePhotoTaken = false;

    // Botones para seleccionar videos
    Button videoButton1, videoButton2, videoButton3, videoButton4, videoButton5;
    Button videoButton6, videoButton7, videoButton8, videoButton9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caricaturas);

        videoView = findViewById(R.id.video1);

        Atrasar = findViewById(R.id.Atrasar);
        Adelantar = findViewById(R.id.Adelantar);
        play_pause = findViewById(R.id.play_pause);
        nuevaActividadButton = findViewById(R.id.Atras);
        nuevaActividadButton2 = findViewById(R.id.Cambiarusuario);
        Atras = findViewById(R.id.Atras);

        nombre = findViewById(R.id.nombre);
        usuarioyedad = findViewById(R.id.usuarioyedad);
        Foto = findViewById(R.id.imgusuario);

        // Inicializar los botones de selección de video
        videoButton1 = findViewById(R.id.AcceleRacers);
        videoButton2 = findViewById(R.id.AcceleRacers1);
        videoButton3 = findViewById(R.id.Battleforce5);
        videoButton4 = findViewById(R.id.battleforce51);
        videoButton5 = findViewById(R.id.johnnytest);
        videoButton6 = findViewById(R.id.johnnytest1);
        videoButton7 = findViewById(R.id.losjovenestitanes);
        videoButton8 = findViewById(R.id.thundercats);
        videoButton9 = findViewById(R.id.dbz);

        // Configurar los oyentes de clic para los botones de selección de video
        videoButton1.setOnClickListener(v -> checkProfilePhotoAndLoadVideo(R.raw.acelera));
        videoButton2.setOnClickListener(v -> checkProfilePhotoAndLoadVideo(R.raw.go));
        videoButton3.setOnClickListener(v -> checkProfilePhotoAndLoadVideo(R.raw.bf5curiosidades));
        videoButton4.setOnClickListener(v -> checkProfilePhotoAndLoadVideo(R.raw.drive_to_survive));
        videoButton5.setOnClickListener(v -> checkProfilePhotoAndLoadVideo(R.raw.johnny_el_lanzallamas));
        videoButton6.setOnClickListener(v -> checkProfilePhotoAndLoadVideo(R.raw.regresoajohnny_mon));
        videoButton7.setOnClickListener(v -> checkProfilePhotoAndLoadVideo(R.raw.jovenes));
        videoButton8.setOnClickListener(v -> checkProfilePhotoAndLoadVideo(R.raw.thundercats_opening));
        videoButton9.setOnClickListener(v -> checkProfilePhotoAndLoadVideo(R.raw.dragon_ball_z));

        play_pause.setOnClickListener(v -> {
            if (videoView.isPlaying()) {
                videoView.pause();
                play_pause.setText("Play");
            } else {
                videoView.start();
                play_pause.setText("Pause");
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

        // Llamar al método para mostrar el mensaje de bienvenida
        displayWelcomeMessage();

        // Configurar el Intent para el nuevo botón
        nuevaActividadButton.setOnClickListener(v -> {
            Intent intent = new Intent(caricaturas.this, MainActivity3.class);
            // Pasar la ruta de la foto de perfil a la nueva actividad
            intent.putExtra("profilePhotoPath", getProfilePhotoPath());
            startActivity(intent);
        });
        nuevaActividadButton.setOnClickListener(v -> {
            Intent intent = new Intent(caricaturas.this, MainActivity4.class);
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

    private void checkProfilePhotoAndLoadVideo(int videoResId) {
        if (!profilePhotoTaken) {
            showProfilePhotoDialog(() -> loadVideo(videoResId));
        } else {
            loadVideo(videoResId);
        }
    }

    private void showProfilePhotoDialog(Runnable onPhotoTaken) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Para poder ver el video es necesario que pongas una foto de perfil.");
        builder.setPositiveButton("Aceptar", (dialog, which) -> takeProfilePhoto(onPhotoTaken));
        builder.setNegativeButton("Cancelar", (dialog, which) -> {
            Intent intent = new Intent(caricaturas.this, MainActivity4.class);
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
                Intent intent = new Intent(caricaturas.this, MainActivity4.class);
                startActivity(intent);
                finish();
            }
        } else {
            Intent intent = new Intent(caricaturas.this, MainActivity4.class);
            startActivity(intent);
            finish();
        }
    }

    private void loadVideo(int videoResId) {
        String videoPath = "android.resource://" + getPackageName() + "/" + videoResId;
        videoView.setVideoURI(Uri.parse(videoPath));
        videoView.start();
        play_pause.setText("Pause");
    }

    private String getProfilePhotoPath() {
        return getFilesDir().getAbsolutePath() + "/profile_photo.png";
    }
}




