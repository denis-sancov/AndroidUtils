package md.sancov.utils.otp

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.google.android.gms.auth.api.phone.SmsRetriever
import md.sancov.utils.ex.parseOtpCode

class OtpResultContract: ActivityResultContract<Intent, String>() {
    override fun createIntent(context: Context, input: Intent): Intent {
        return input
    }

    override fun parseResult(resultCode: Int, intent: Intent?): String? {
        if (resultCode != Activity.RESULT_OK || intent == null) {
            return null
        }
        return intent.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)?.parseOtpCode()
    }
}