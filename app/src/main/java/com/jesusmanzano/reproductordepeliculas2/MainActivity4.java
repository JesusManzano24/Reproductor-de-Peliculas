package com.jesusmanzano.reproductordepeliculas2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MainActivity4 extends AppCompatActivity {
    private Button Menu;
    private Button Cambiar;
    private TextView Textobienvenido;
    private ImageView imageViewCaricaturas;
    private ImageView imageViewaccion;
    private ImageView imageViewterror;
    private TextView textViewCaricaturas;
    private TextView textViewAccion;
    private TextView textViewTerror;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main4);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Textobienvenido = findViewById(R.id.MostrarBienvenida);
        imageViewCaricaturas = findViewById(R.id.caricatura);
        imageViewaccion = findViewById(R.id.accion);
        imageViewterror = findViewById(R.id.terror);
        textViewCaricaturas = findViewById(R.id.text_caricatura);
        textViewAccion = findViewById(R.id.text_accion);
        textViewTerror = findViewById(R.id.text_terror);

        // Mostrar el nombre y la edad del usuario
        displayWelcomeMessage();
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
        Textobienvenido.setText("Hola " + nombre + ", de acuerdo a tu edad " + edad + " tienes disponible");

        // Clasificación de contenido basada en la edad
        int edadInt = Integer.parseInt(edad);
        if (edadInt < 12) {
            imageViewCaricaturas.setImageResource(R.drawable.cartoon_network);
            textViewCaricaturas.setText("Caricaturas");
            imageViewCaricaturas.setEnabled(true);
            imageViewCaricaturas.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity4.this, caricaturas.class);
                startActivity(intent);
            });

            textViewAccion.setVisibility(View.GONE);
            imageViewaccion.setVisibility(View.GONE);
            textViewTerror.setVisibility(View.GONE);
            imageViewterror.setVisibility(View.GONE);
        } else if (edadInt >= 12 && edadInt <= 18) {
            imageViewCaricaturas.setImageResource(R.drawable.cartoon_network);
            textViewCaricaturas.setText("Caricaturas");
            imageViewCaricaturas.setEnabled(true);
            imageViewCaricaturas.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity4.this, caricaturas.class);
                startActivity(intent);
            });

            imageViewaccion.setImageResource(R.drawable.accion);
            textViewAccion.setText("Acción");
            imageViewaccion.setEnabled(true);
            imageViewaccion.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity4.this, accion.class);
                startActivity(intent);
            });

            textViewTerror.setVisibility(View.GONE);
            imageViewterror.setVisibility(View.GONE);
        } else if (edadInt > 18) {
            imageViewCaricaturas.setImageResource(R.drawable.cartoon_network);
            textViewCaricaturas.setText("Caricaturas");
            imageViewCaricaturas.setEnabled(true);
            imageViewCaricaturas.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity4.this, caricaturas.class);
                startActivity(intent);
            });

            imageViewaccion.setImageResource(R.drawable.accion);
            textViewAccion.setText("Acción");
            imageViewaccion.setEnabled(true);
            imageViewaccion.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity4.this, accion.class);
                startActivity(intent);
            });

            imageViewterror.setImageResource(R.drawable.terror);
            textViewTerror.setText("Terror");
            imageViewterror.setEnabled(true);
            imageViewterror.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity4.this, terror.class);
                startActivity(intent);
            });
        }
    }
}



