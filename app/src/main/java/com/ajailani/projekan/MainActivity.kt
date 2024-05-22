package com.ajailani.projekan

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.ajailani.projekan.ui.Navigation
import com.ajailani.projekan.ui.Screen
import com.ajailani.projekan.ui.common.SharedViewModel
import com.ajailani.projekan.ui.feature.splash.SplashViewModel
import com.ajailani.projekan.ui.theme.ProjekanTheme
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.appsflyer.deeplink.DeepLinkListener
import com.appsflyer.deeplink.DeepLinkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Objects

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val splashViewModel: SplashViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels {
        SavedStateViewModelFactory(application, this)
    }
    val LOG_TAG: String = "AppsFlyerFeedMeApp"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val afDevKey = BuildConfig.APPSFLYER_KEY
        val appsflyer = AppsFlyerLib.getInstance()
        appsflyer.setDebugLog(true)
        appsflyer.subscribeForDeepLink(DeepLinkListener { deepLinkResult ->
            val dlStatus = deepLinkResult.status
            when (dlStatus) {
                DeepLinkResult.Status.FOUND -> {
                    Log.d(LOG_TAG, "Deep link found")
                }
                DeepLinkResult.Status.NOT_FOUND -> {
                    Log.d(LOG_TAG, "Deep link not found")
                    return@DeepLinkListener
                }
                else -> {
                    val dlError = deepLinkResult.error
                    Log.d(LOG_TAG, "There was an error getting Deep Link data: $dlError")
                    return@DeepLinkListener
                }
            }
            val deepLinkObj = deepLinkResult.deepLink
            try {
                Log.d(LOG_TAG, "The DeepLink data is: $deepLinkObj")
            } catch (e: Exception) {
                Log.d(LOG_TAG, "DeepLink data came back null")
                return@DeepLinkListener
            }
            if (deepLinkObj.isDeferred() == true) {
                Log.d(LOG_TAG, "This is a deferred deep link")
            } else {
                Log.d(LOG_TAG, "This is a direct deep link")
            }
            try {
                deepLinkObj.deepLinkValue?.let {
                    sharedViewModel.onDeeplinkProjectIdChanged(it)
                    Log.d(LOG_TAG, "The DeepLink will route to: $it")
                }
                val dlData = deepLinkObj.clickEvent
                Log.d("dlData", "dlData: $dlData")
            } catch (e: Exception) {
                Log.d(LOG_TAG, "Custom param was not found in DeepLink data")
                return@DeepLinkListener
            }
            val dlData = deepLinkObj.clickEvent
            if (dlData.has("deep_link_sub2")) {
                var referrerId: String? = ""
                referrerId = deepLinkObj.getStringValue("deep_link_sub2")
                Log.d(LOG_TAG, "The referrerID is: $referrerId")
            } else {
                Log.d(LOG_TAG, "deep_link_sub2/Referrer ID not found")
            }
        })

        val conversionListener: AppsFlyerConversionListener = object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(conversionData: Map<String, Any>) {
                val campaign = conversionData["campaign"]
                val mediaSource = conversionData["media_source"]
                Log.d("onConversionDataSuccess", "Campaign: $campaign, Media Source: $mediaSource")
                Log.d(LOG_TAG, "onConversionDataSuccess $conversionData")
                for (attrName in conversionData.keys) Log.d(
                    LOG_TAG,
                    "Conversion attribute: " + attrName + " = " + conversionData[attrName]
                )
                val status = Objects.requireNonNull(conversionData["af_status"]).toString()
                if (status == "Non-organic") {
                    if (Objects.requireNonNull(conversionData["is_first_launch"])
                            .toString() == "true"
                    ) {
                        Log.d(LOG_TAG, "Conversion: First Launch")
                    } else {
                        Log.d(LOG_TAG, "Conversion: Not First Launch")
                    }
                } else {
                    Log.d(LOG_TAG, "Conversion: This is an organic install.")
                }
            }

            override fun onConversionDataFail(errorMessage: String) {
                Log.d(LOG_TAG, "error getting conversion data: $errorMessage")
            }

            override fun onAppOpenAttribution(attributionData: Map<String, String>) {
                Log.d(LOG_TAG, "onAppOpenAttribution: This is fake call. $attributionData")
            }

            override fun onAttributionFailure(errorMessage: String) {
                Log.d(LOG_TAG, "error onAttributionFailure : $errorMessage")
            }
        }

        appsflyer.init(afDevKey, conversionListener, this)
        appsflyer.start(this)
        installSplashScreen()

        lifecycleScope.launch {
            splashViewModel.getAccessToken().first().let { accessToken ->
                val startDestination = if (accessToken != "") {
                    Screen.Home.route
                } else {
                    Screen.Welcome.route
                }

                setContent {
                    ProjekanTheme {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colors.background
                        ) {
                            val projectId = sharedViewModel.deeplinkProjectId

                            Content(
                                projectId = projectId,
                                startDestination = startDestination,
                                sharedViewModel = sharedViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Content(
    projectId: String,
    startDestination: String,
    sharedViewModel: SharedViewModel
) {
    val navController = rememberNavController()

    Navigation(
        projectId = projectId,
        navController = navController,
        startDestination = startDestination,
        sharedViewModel = sharedViewModel
    )
}