package com.jesusmanzano.reproductordepeliculas2;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private float bounceX = 0; // Posición horizontal de rebote
    private float bounceY = 0; // Posición vertical de rebote
    private float velocityX = 5; // Velocidad horizontal inicial
    private float velocityY = 5; // Velocidad vertical inicial
    private int screenWidth;
    private int screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView splashImage = findViewById(R.id.splashImage);
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;

        // Crear una animación de rebote en las esquinas
        ValueAnimator bounceAnimator = ValueAnimator.ofFloat(0, 1);
        bounceAnimator.setDuration(16); // Duración de la animación en milisegundos (aproximadamente un frame)
        bounceAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        bounceAnimator.setRepeatCount(ValueAnimator.INFINITE); // Repetir infinitamente

        bounceAnimator.addUpdateListener(animation -> {
            float progress = (float) animation.getAnimatedValue();
            bounceX += velocityX;
            bounceY += velocityY;

            // Cambiar de dirección si se alcanzan los límites de la pantalla
            if (bounceX < 0 || bounceX > screenWidth - splashImage.getWidth()) {
                velocityX *= -1;
            }
            if (bounceY < 0 || bounceY > screenHeight - splashImage.getHeight()) {
                velocityY *= -1;
            }

            splashImage.setTranslationX(bounceX);
            splashImage.setTranslationY(bounceY);
        });

        bounceAnimator.start();

        // Redirigir después de 3 segundos
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent);
            finish();
        }, 5000);
    }
}


