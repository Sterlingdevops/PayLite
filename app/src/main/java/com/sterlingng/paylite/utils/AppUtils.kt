package com.sterlingng.paylite.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.util.*

object AppUtils {

    val gson = gson()

    fun createMessageID(): String {
        return UUID.randomUUID().toString().replace("-", "")
    }

    private fun gson(): Gson {
        return GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()
    }

    fun hasInternetConnection(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo = cm.activeNetworkInfo
        return activeNetwork.isConnectedOrConnecting
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
