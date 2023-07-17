package com.fifty.fixitnow.core.presentation.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract
import com.fifty.fixitnow.core.domain.util.getFileName
import com.yalantis.ucrop.UCrop
import com.yalantis.ucrop.UCrop.RESULT_ERROR
import java.io.File

class CropActivityResultContract(
    private val aspectRatioX: Float? = null,
    private val aspectRatioY: Float? = null
) : ActivityResultContract<Uri, Uri?>() {

    override fun createIntent(context: Context, input: Uri): Intent {
        val imageUri = Uri.fromFile(
            File(
                context.cacheDir,
                context.contentResolver.getFileName(input)
            )
        )
        return if (aspectRatioX != null && aspectRatioY != null) {
            UCrop.of(input, imageUri)
                .withAspectRatio(aspectRatioX, aspectRatioY)
                .getIntent(context)
        } else {
            UCrop.of(input, imageUri)
                .getIntent(context)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        if (intent == null) {
            return null
        }
        if (resultCode == RESULT_ERROR) {
            val error = UCrop.getError(intent)
            error?.printStackTrace()
        }
        return UCrop.getOutput(intent)
    }

}