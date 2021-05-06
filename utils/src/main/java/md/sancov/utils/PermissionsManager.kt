package md.sancov.utils

import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class PermissionsManager(private val activity: FragmentActivity) : DefaultLifecycleObserver {
    private val state = MutableStateFlow(Status.Idle)

    private lateinit var launcher: ActivityResultLauncher<Array<String>>

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)

        val registry = activity.activityResultRegistry
        val contact = ActivityResultContracts.RequestMultiplePermissions()

        launcher = registry.register(KEY, owner, contact) {
            val status = it.values.firstOrNull { status -> status == false } ?: true
            state.value = if (status) Status.Accepted else Status.Rejected
        }
    }

    fun askForPermission(permission: String, result: (Boolean) -> Unit) {
        askForPermissions(arrayOf(permission), result)
    }

    fun askForPermissions(permissions: Array<String>, result: (Boolean) -> Unit) {
        if (hasPermissions(permissions)) {
            result(true)
            return
        }

        state.value = Status.Idle

        launcher.launch(permissions)

        activity.lifecycleScope.launch(Dispatchers.IO) {
            val status = state.filter { it != Status.Idle }.first()

            withContext(Dispatchers.Main) {
                result(status == Status.Accepted)
            }
        }
    }

    private fun hasPermissions(permissions: Array<String>): Boolean {
        val notGranted = permissions.firstOrNull {
            ContextCompat.checkSelfPermission(activity, it) != PackageManager.PERMISSION_GRANTED
        }

        return notGranted == null
    }

    private enum class Status {
        Idle, Accepted, Rejected
    }

    companion object {
        private const val KEY = "PERMISSION_MANAGER_KEY"
    }
}