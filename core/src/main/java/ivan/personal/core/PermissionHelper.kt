package ivan.personal.core

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.IntDef
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import javax.inject.Inject

class PermissionHelper @Inject constructor() {

    companion object {
        @IntDef(
            PERMISSION_STATUS_GRANTED,
            PERMISSION_STATUS_RATIONALE,
            PERMISSION_STATUS_DENIED
        )
        @Retention(AnnotationRetention.SOURCE)
        annotation class PermissionStatus

        const val PERMISSION_STATUS_GRANTED = 0
        const val PERMISSION_STATUS_RATIONALE = 1
        const val PERMISSION_STATUS_DENIED = 2
    }

    /**
     * Check the status of [permission]
     *
     * @return [PermissionStatus]
     */
    @PermissionStatus
    fun checkPermissionStatus(activity: AppCompatActivity, permission: String): Int =
        when {
            // permission granted
            isPermissionGranted(
                context = activity,
                permission = permission
            ) -> PERMISSION_STATUS_GRANTED

            // need to provide more information to grant the permission
            ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                permission
            ) -> PERMISSION_STATUS_RATIONALE

            // denied
            else -> PERMISSION_STATUS_DENIED
        }

    /**
     * Check the [permission] is granted or not
     */
    private fun isPermissionGranted(context: Context, permission: String): Boolean =
        ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED

    inline fun requestPermission(
        activity: AppCompatActivity,
        permission: String,
        crossinline callback: (Int) -> Unit
    ) {
        val requestPermissionLauncher =
            activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                callback.invoke(checkPermissionStatus(activity = activity, permission = permission))
            }
    }
}

/**
 * Create [ActivityResultLauncher] for request permission
 */
fun Fragment.createPermissionLauncher(callback: (Boolean) -> Unit): ActivityResultLauncher<String> =
    registerForActivityResult(ActivityResultContracts.RequestPermission()) { callback.invoke(it) }