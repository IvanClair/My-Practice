package ivan.personal.core

import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.IntDef
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

@Target(AnnotationTarget.TYPE, AnnotationTarget.VALUE_PARAMETER)
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
 * Create [ActivityResultLauncher] for request permission
 */
inline fun AppCompatActivity.createPermissionLauncher(
    permission: String,
    crossinline callback: (@PermissionStatus Int) -> Unit
): ActivityResultLauncher<String> =
    registerForActivityResult(ActivityResultContracts.RequestPermission()) {
        val status = when {
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
        callback.invoke(status)
    }