package com.jesusmanzano.reproductordepeliculas2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity3 extends AppCompatActivity {
    private Button Menu;
    private Button Cambiar;
    private TextView Textobienvenido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Menu = findViewById(R.id.Menu);
        Cambiar = findViewById(R.id.Cambiar);
        Textobienvenido = findViewById(R.id.MostrarBienvenida);

        // Mostrar el nombre del usuario
        displayWelcomeMessage();

        Menu.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity3.this, MainActivity4.class);
            startActivity(intent);
        });
    }

    private String readUserData() {
        String filename = "UserData.txt";
        StringBuilder stringBuilder = new StringBuilder();

        try (FileInputStream fis = openFileInput(filename);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader bufferedReader = new BufferedReader(isr)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

    private void displayWelcomeMessage() {
        String userData = readUserData();
        String nombre = "";

        // Extraer el nombre del usuario
        for (String line : userData.split("\n")) {
            if (line.startsWith("nombre:")) {
                nombre = line.split(":")[1];
                break;
            }
        }

        // Mostrar el mensaje de bienvenida
        Textobienvenido.setText("Bienvenido " + nombre);
    }
}
