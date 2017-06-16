package com.example.igor.controlstate

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.github.anastr.speedviewlib.SpeedView
import java.util.Timer
import java.util.TimerTask

class MainActivity : AppCompatActivity() {

    var sensorManager: SensorManager? = null
    val sensorList: List<Sensor>? = null
    var accelerometer: Sensor? = null
    var speedView: SpeedView? = null
    var acceleration = 0f

    val workedSensorListener = object : SensorEventListener {

        val callibration = SensorManager.STANDARD_GRAVITY.toDouble()

        /***
         * event.sensor -ссылка на датчик
         * event.accuracy - точность значения датчика
         * event.timestamp - время возникновения события (нс)
         * event.values - массив  значений
         * @param event
         */
        override fun onSensorChanged(event: SensorEvent) {
            val x = event.values[0].toDouble()
            val y = event.values[1].toDouble()
            val z = event.values[2].toDouble()

            //определяем модуль вектора
            val _xyz = Math.sqrt(Math.pow(x, 2.0) + Math.pow(y, 2.0) + Math.pow(z, 2.0))
            acceleration = Math.abs((_xyz - callibration).toFloat())
        }
        /***
         * позволяет отслеживaть изменение точности передаваемых значений,
         * определяемой одной из констант:
         * SensorManager.SENSOR_STATUS_ACCURACY_LOW — низкая точность,
         * SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM — средняя точность, возможна калибровка,
         * SensorManager.SENSOR_STATUS_ACCURACY_HIGH — высокая точность,
         * SensorManager.SENSOR_STATUS_UNRELIABLE — данные недостоверны, нужна калибровка.
         * @param sensor
         * *
         * @param accuracy
         */
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        speedView = findViewById(R.id.gauge) as SpeedView

        val timer = Timer("Timer")
        //обновляем раз в 100 мс
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                updateGUI()
            }
        }, 0, 100)
    }

    private fun updateGUI() {
        runOnUiThread { speedView!!.speedTo(acceleration / SensorManager.STANDARD_GRAVITY) }
    }

    /***
     * регистрация датчика
     */
    override fun onResume() {
        super.onResume()
        sensorManager!!.registerListener(workedSensorListener, accelerometer,
                SensorManager.SENSOR_DELAY_FASTEST)
    }

    override fun onPause() {
        super.onPause()
        sensorManager!!.unregisterListener(workedSensorListener)
    }
}