package com.example.igor.controlstate;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.anastr.speedviewlib.SpeedView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private List<Sensor> sensorList;
    private Sensor accelerometer;
    private SpeedView speedView;
    private float acceleration = 0;

    private final SensorEventListener workedSensorListener = new SensorEventListener() {


        private final double callibration = SensorManager.STANDARD_GRAVITY;

        /***
         * event.sensor -ссылка на датчик
         * event.accuracy - точность значения датчика
         * event.timestamp - время возникновения события (нс)
         * event.values - массив  значений
         * @param event
         */
        @Override
        public void onSensorChanged(SensorEvent event) {

            double x = event.values[0];
            double y = event.values[1];
            double z = event.values[2];

            //определяем модуль вектора
            double _xyz = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2));
            acceleration = Math.abs((float) (_xyz - callibration));

        }

        /***
         * позволяет отслеживaть изменение точности передаваемых значений,
         * определяемой одной из констант:
         * SensorManager.SENSOR_STATUS_ACCURACY_LOW — низкая точность,
         * SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM — средняя точность, возможна калибровка,
         * SensorManager.SENSOR_STATUS_ACCURACY_HIGH — высокая точность,
         * SensorManager.SENSOR_STATUS_UNRELIABLE — данные недостоверны, нужна калибровка.
         * @param sensor
         * @param accuracy
         */
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        speedView = (SpeedView) findViewById(R.id.gauge);

        Timer timer = new Timer("Timer");
        //обновляем раз в 100 мс
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateGUI();
            }
        },0 ,100);
    }

    private void updateGUI(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                speedView.speedTo(acceleration/SensorManager.STANDARD_GRAVITY);
            }
        });
    }

    /***
     * регистрация датчика
     */
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(workedSensorListener,accelerometer,
                SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(workedSensorListener);
    }
}
