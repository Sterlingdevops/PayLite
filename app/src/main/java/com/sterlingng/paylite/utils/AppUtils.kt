package com.sterlingng.paylite.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.LongSerializationPolicy
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*


object AppUtils {

    val gson = gson()

    fun createId(): String {
        return UUID.randomUUID().toString().replace("-", "")
    }

    /**
     * ref https://stackoverflow.com/questions/10174898/how-to-check-whether-a-given-string-is-valid-json-in-java
     */
    fun isJSONValid(test: String): Boolean {
        try {
            JSONObject(test)
        } catch (ex: JSONException) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            try {
                JSONArray(test)
            } catch (ex1: JSONException) {
                return false
            }
        }
        return true
    }

    private fun gson(): Gson {
        return GsonBuilder()
                .setLongSerializationPolicy(LongSerializationPolicy.STRING)
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
    }

    fun hasInternetConnection(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val activeNetwork: NetworkInfo? = cm?.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    //    public static boolean checkPlayServices(Activity activity) {
    //        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
    //        int resultCode = apiAvailability.isGooglePlayServicesAvailable(activity);
    //        if (resultCode != ConnectionResult.SUCCESS) {
    //            if (apiAvailability.isUserResolvableError(resultCode)) {
    //                apiAvailability.getErrorDialog(activity, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
    //                        .show();
    //            } else {
    //                activity.finish();
    //            }
    //            return false;
    //        }
    //        return true;
    //    }

}// This class is not publicly instantiable
