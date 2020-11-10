package ivan.personal.core

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

/**
 * Create [Intent] to system settings
 */
fun Context.createSettingsIntent(): Intent =
    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        .apply { data = Uri.fromParts("package", packageName, null) }