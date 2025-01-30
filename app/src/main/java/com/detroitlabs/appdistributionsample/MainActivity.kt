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
                            productFlavor = BuildConfig.FLAVOR,
                            buildType = BuildConfig.BUILD_TYPE,
                            applicationId = BuildConfig.APPLICATION_ID,
                            baseUrl = BuildConfig.API_BASE_URL,
                            apiKey = BuildConfig.API_KEY
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(productFlavor: String, buildType: String, applicationId: String, baseUrl: String, apiKey: String) {
    Column {
        Text(text = "Product flavor: $productFlavor")
        Text(text = "Build type: $buildType")
        Text(text = "Build variant: $productFlavor${buildType.replaceFirstChar(Char::titlecase)}")
        Text(text = "Application ID: $applicationId")
        Text(text = "API base URL: $baseUrl")
        Text(text = "API key: $apiKey")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppDistributionSampleTheme {
        Greeting("[product flavor]", "[build type]", "[application ID]", "[base URL]", "[API key]")
    }
}