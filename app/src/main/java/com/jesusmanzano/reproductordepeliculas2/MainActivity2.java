package com.jesusmanzano.reproductordepeliculas2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat.Type;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity2 extends AppCompatActivity {
    private Button playButton;
    private EditText Nombre;
    private EditText Edad;
    private Button Registro;
    private Spinner Genero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        playButton = findViewById(R.id.playButton);
        playButton.setOnClickListener(v -> showRegisterDialog());
    }

    private void showRegisterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialogo, null);
        builder.setView(dialogView);

        Nombre = dialogView.findViewById(R.id.txtNombre);
        Edad = dialogView.findViewById(R.id.txtEdad);
        Genero = dialogView.findViewById(R.id.spinnerGender);
        Registro = dialogView.findViewById(R.id.buttonRegister);

        // Configurar el Spinner de Género
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Genero.setAdapter(adapter);

        Registro.setOnClickListener(v -> {
            String name = Nombre.getText().toString().trim();
            String ageStr = Edad.getText().toString().trim();
            String gender = Genero.getSelectedItem().toString();

            if (name.isEmpty() || ageStr.isEmpty() || gender.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            int age = Integer.parseInt(ageStr);
            if (age < 7 || age > 100) {
                Toast.makeText(this, "La edad debe estar entre 7 y 100", Toast.LENGTH_SHORT).show();
                return;
            }

            saveUserData(name, age, gender);
            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();

            // Redirigir a otra pantalla después del registro
            Intent intent = new Intent(MainActivity2.this, MainActivity3.class);
            startActivity(intent);
            finish();
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void saveUserData(String nombre, int Edad, String genero) {
        String filename = "UserData.txt";
        String fileContents = "nombre:" + nombre + "\n" + "Edad:" + Edad + "\n" + "genero:" + genero;
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(filename, Context.MODE_PRIVATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            if (fos != null) {
                fos.write(fileContents.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (fos != null) {
                fos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

