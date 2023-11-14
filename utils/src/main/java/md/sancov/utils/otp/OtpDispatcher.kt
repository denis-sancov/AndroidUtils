package md.sancov.utils.otp

import android.content.Intent
import android.content.IntentFilter
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.phone.SmsRetriever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import md.sancov.utils.R

data class OtpDispatcher(private val activity: FragmentActivity) : DefaultLifecycleObserver {
    private var state = MutableStateFlow<State>(State.Idle)

    private val sender: String = activity.resources.getString(R.string.sms_user_consent)

    private val receiver = OtpBroadcastReceiver {
        if (it is OtpBroadcastResult.Success && state.value is State.WaitingForSms) {
            state.value = State.WaitingForResult
            getOtp.launch(it.intent)
        }
    }

    private lateinit var getOtp : ActivityResultLauncher<Intent>

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)

        getOtp = activity.activityResultRegistry.register(OTP_MANAGER_KEY, owner, OtpResultContract()) { otp ->
            state.value = State.Result(otp)
        }
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)

        val filter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            activity.registerReceiver(receiver, filter, Context.RECEIVER_EXPORTED)
        else
            activity.registerReceiver(receiver, filter)
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        activity.unregisterReceiver(receiver)
    }

    fun startOtpObserver() {
        state.value = State.WaitingForSms

        SmsRetriever
            .getClient(activity)
            .startSmsUserConsent(sender)
    }

    fun retrieve(callback: (String?) -> Unit) {
        if (state.value is State.Idle) {
            callback(null)
            return
        }

        activity.lifecycleScope.launch(Dispatchers.IO) {
            if (state.value is State.WaitingForSms) {
                delay(10000)

                if (state.value is State.WaitingForSms) {
                    state.value = State.Idle

                    withContext(Dispatchers.Main) {
                        callback(null)
                    }

                    return@launch
                }
            }

            val otp = state
                .filterIsInstance<State.Result>()
                .map { it.otp }
                .first()

            state.value = State.Idle

            withContext(Dispatchers.Main) {
                callback(otp)
            }
        }
    }

    private sealed class State {
        data class Result(val otp: String?): State()

        object WaitingForSms: State()
        object WaitingForResult: State()

        object Idle: State()
    }

    companion object {
        private const val OTP_MANAGER_KEY = "OTP_MANAGER_KEY"
    }
}
