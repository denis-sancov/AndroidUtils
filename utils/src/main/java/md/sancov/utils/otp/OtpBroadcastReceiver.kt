package md.sancov.utils.otp

import android.content.ActivityNotFoundException
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.Status

sealed class OtpBroadcastResult {
    data class Success(val intent: Intent): OtpBroadcastResult()
    data class Error(val throwable: Throwable?): OtpBroadcastResult()
}

class OtpBroadcastReceiver(val listener: (OtpBroadcastResult) -> Unit): BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION != intent.action) {
            return
        }

        val extras = intent.extras
        val smsRetrieverStatus = extras?.get(SmsRetriever.EXTRA_STATUS) as Status

        val result = when (smsRetrieverStatus.statusCode) {
            CommonStatusCodes.SUCCESS -> {
                val consentIntent = extras.getParcelable<Intent>(SmsRetriever.EXTRA_CONSENT_INTENT)
                try {
                    if (consentIntent != null) {
                        OtpBroadcastResult.Success(consentIntent)
                    } else {
                        OtpBroadcastResult.Error(null)
                    }
                } catch (e: ActivityNotFoundException) {
                    OtpBroadcastResult.Error(e)
                }
            }
            else -> OtpBroadcastResult.Error(null)
        }

        listener(result)
    }
}