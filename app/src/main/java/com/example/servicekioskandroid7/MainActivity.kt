package com.example.servicekioskandroid7

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.servicekioskandroid7.callback.ReadTCPCallBack
import com.example.servicekioskandroid7.ui.theme.ServiceKioskAndroid7Theme

class MainActivity : ComponentActivity() {
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Intent>

    companion object {
        private const val SYSTEM_ALERT_WINDOW_PERMISSION_CODE = 100
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        requestPermissionLauncher = registerForActivityResult(StartActivityForResult()) { result ->
            if (Settings.canDrawOverlays(this)) {
                startService(this)
            }
        }

        if (!Settings.canDrawOverlays(this)) {
            val intent =
                Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
            requestPermissionLauncher.launch(intent)
        } else {
            startService(this)
            Log.d("MainActivity", "grant: " + this)

        }
    }
}


fun startService(context: Context) {
    val serviceIntent = Intent(context, ServiceKiosk::class.java)
    context.startService(serviceIntent)
    ReadTCPCallBack.startAppMethod()
    Toast.makeText(
        context,
        "Đã bật service.",
        Toast.LENGTH_SHORT
    ).show()
}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ServiceKioskAndroid7Theme {
        Greeting("Android")
    }
}