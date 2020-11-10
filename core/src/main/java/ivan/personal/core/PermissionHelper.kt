package ivan.personal.core

import android.app.Activity
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.IntDef
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

@Target(AnnotationTarget.TYPE, AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.FUNCTION)
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

/**
 * Create [ActivityResultLauncher] for permission request
 */
inline fun Fragment.createPermissionLauncher(
    permission: String,
    crossinline callback: (@PermissionStatus Int) -> Unit
): ActivityResultLauncher<String> =
    registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        activity?.run {
            val status = checkPermissionStatus(permission = permission)
            callback.invoke(status)
        }
    }

/**
 * Check [PermissionStatus] of the [permission]
 */
@PermissionStatus
infix fun Activity.checkPermissionStatus(permission: String): Int =
    when {
        // permission granted
        ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED -> PERMISSION_STATUS_GRANTED

        // need to provide more information to explain why you need the permission
        ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            permission
        ) -> PERMISSION_STATUS_RATIONALE

        // denied
        else -> PERMISSION_STATUS_DENIED
    }
