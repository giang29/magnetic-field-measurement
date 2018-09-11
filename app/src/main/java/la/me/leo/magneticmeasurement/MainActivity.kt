package la.me.leo.magneticmeasurement

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.x_cmf
import kotlinx.android.synthetic.main.activity_main.x_tcmf
import kotlinx.android.synthetic.main.activity_main.x_umf
import kotlinx.android.synthetic.main.activity_main.y_cmf
import kotlinx.android.synthetic.main.activity_main.y_tcmf
import kotlinx.android.synthetic.main.activity_main.y_umf
import kotlinx.android.synthetic.main.activity_main.z_cmf
import kotlinx.android.synthetic.main.activity_main.z_tcmf
import kotlinx.android.synthetic.main.activity_main.z_umf

class MainActivity : AppCompatActivity(), SensorEventListener {
    private val sm by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    private val uncalibratedMagneticSensor by lazy {
        sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED)
    }
    private val calibratedMagneticSensor by lazy {
        sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    override fun onResume() {
        super.onResume()
        uncalibratedMagneticSensor?.let {
            sm.registerListener(this, it, 3000000)
        } ?: turnViewsToVisible(setOf(R.id.error_text))
        calibratedMagneticSensor?.let {
            sm.registerListener(this, it, 3000000)
        }
    }
    override fun onPause() {
        super.onPause()
        sm.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        when(event.sensor.type) {
            Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED -> {
                x_umf.text = event.values[0].toString()
                y_umf.text = event.values[1].toString()
                z_umf.text = event.values[2].toString()
                x_tcmf.text = (event.values[0] + event.values[3]).toString()
                y_tcmf.text = (event.values[1] + event.values[4]).toString()
                z_tcmf.text = (event.values[2] + event.values[5]).toString()
            }
            Sensor.TYPE_MAGNETIC_FIELD -> {
                x_cmf.text = event.values[0].toString()
                y_cmf.text = event.values[1].toString()
                z_cmf.text = event.values[2].toString()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    private fun turnViewsToVisible(visibleViewIds: Set<Int>) {
        val rootView = findViewById<ViewGroup>(android.R.id.content).getChildAt(0)
        (rootView as? ViewGroup)?.turnViewsToVisible(visibleViewIds)
    }
}

