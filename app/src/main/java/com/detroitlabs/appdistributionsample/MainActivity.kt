package com.detroitlabs.appdistributionsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.detroitlabs.appdistributionsample.ui.theme.AppDistributionSampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppDistributionSampleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier.fillMaxSize().padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        Greeting(
                            buildType = BuildConfig.BUILD_LABEL,
                            baseUrl = BuildConfig.API_BASE_URL
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(buildType: String, baseUrl: String) {
    Column {
        Text(text = "Build type: $buildType")
        Text(text = "API base URL: $baseUrl")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppDistributionSampleTheme {
        Greeting("[build type]", "[base URL]")
    }
}