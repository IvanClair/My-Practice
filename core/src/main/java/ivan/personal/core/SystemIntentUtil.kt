package ivan.personal.core

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.telephony.AccessNetworkConstants
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

/**
 * Create [Intent] to system settings
 */
fun Context.createSettingsIntent(): Intent =
    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        .apply { data = Uri.fromParts("package", packageName, null) }