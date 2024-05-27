package com.ajailani.projekan

import android.app.Application
import android.content.Intent
import android.util.Log
import com.ajailani.projekan.data.local.PreferencesDataStore
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.appsflyer.deeplink.DeepLinkListener
import com.appsflyer.deeplink.DeepLinkResult
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Objects

@HiltAndroidApp
class App : Application(){
    val LOG_TAG : String = "AppsFlyerFeedMeApp"
    var myConversionData : Map<String, Any>? = null
    override fun onCreate() {
        super.onCreate()
        val afDevKey = BuildConfig.APPSFLYER_KEY
        val appsflyer = AppsFlyerLib.getInstance()
        //appsflyer.setDebugLog(true)
        appsflyer.subscribeForDeepLink(DeepLinkListener { deepLinkResult ->
            val dlStatus = deepLinkResult.status
            when (dlStatus) {
                DeepLinkResult.Status.FOUND -> {
                    Log.e(LOG_TAG, "Deep link found")
                }
                DeepLinkResult.Status.NOT_FOUND -> {
                    Log.e(LOG_TAG, "Deep link not found")
                    return@DeepLinkListener
                }
                else -> {
                    val dlError = deepLinkResult.error
                    Log.e(LOG_TAG, "There was an error getting Deep Link data: $dlError")
                    return@DeepLinkListener
                }
            }
            val deepLinkObj = deepLinkResult.deepLink
            try {
                Log.e(LOG_TAG, "The DeepLink data is: $deepLinkObj")
            } catch (e: Exception) {
                Log.e(LOG_TAG, "DeepLink data came back null")
                return@DeepLinkListener
            }
            if (deepLinkObj.isDeferred() == true) {
                Log.e(LOG_TAG, "This is a deferred deep link")
            } else {
                Log.e(LOG_TAG, "This is a direct deep link")
            }
            try {
                handleDeepLink(deepLinkResult)
                deepLinkObj.deepLinkValue?.let {
                    Log.e(LOG_TAG, "The DeepLink will route to: $it")
                }
                val dlData = deepLinkObj.clickEvent
                Log.e("dlData", "dlData: $dlData")
            } catch (e: Exception) {
                Log.e(LOG_TAG, "Custom param was not found in DeepLink data")
                return@DeepLinkListener
            }
            val dlData = deepLinkObj.clickEvent
            if (dlData.has("deep_link_sub2")) {
                var referrerId: String? = ""
                referrerId = deepLinkObj.getStringValue("deep_link_sub2")
                Log.e(LOG_TAG, "The referrerID is: $referrerId")
            } else {
                Log.e(LOG_TAG, "deep_link_sub2/Referrer ID not found")
            }
        })

        val conversionListener: AppsFlyerConversionListener = object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(conversionData: Map<String, Any>) {
                val preferencesDataStore = PreferencesDataStore(this@App)
                GlobalScope.launch {
                    preferencesDataStore.saveConversionData(conversionData.toString())
                }
                val campaign = conversionData["campaign"]
                val mediaSource = conversionData["media_source"]
                Log.e("onConversionDataSuccess", "Campaign: $campaign, Media Source: $mediaSource")
                Log.e(LOG_TAG, "onConversionDataSuccess $conversionData")
                for (attrName in conversionData.keys) Log.e(
                    LOG_TAG,
                    "Conversion attribute: " + attrName + " = " + conversionData[attrName]
                )
                val status = Objects.requireNonNull(conversionData["af_status"]).toString()
                if (status == "Non-organic") {
                    if (Objects.requireNonNull(conversionData["is_first_launch"])
                            .toString() == "true"
                    ) {
                        Log.e(LOG_TAG, "Conversion: First Launch")
                    } else {
                        Log.e(LOG_TAG, "Conversion: Not First Launch")
                    }
                } else {
                    Log.e(LOG_TAG, "Conversion: This is an organic install.")
                }
            }

            override fun onConversionDataFail(errorMessage: String) {
                Log.e(LOG_TAG, "error getting conversion data: $errorMessage")
            }

            override fun onAppOpenAttribution(attributionData: Map<String, String>) {
                Log.e(LOG_TAG, "onAppOpenAttribution: This is fake call. $attributionData")
            }

            override fun onAttributionFailure(errorMessage: String) {
                Log.e(LOG_TAG, "error onAttributionFailure : $errorMessage")
            }
        }
        appsflyer.init(afDevKey, conversionListener, this)
        appsflyer.start(this)
    }
    private fun handleDeepLink(deepLinkResult: DeepLinkResult) {
        val deepLinkData = deepLinkResult.deepLink
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("deepLinkData", deepLinkData.toString())
            putExtra("myConversionData", myConversionData.toString())
        }
        startActivity(intent)
    }
}