package com.fifty.fixitnow.featureworker.presentation.workerprofile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.fifty.fixitnow.core.presentation.ui.theme.SizeMedium
import com.fifty.fixitnow.core.presentation.ui.theme.SizeSmall

@OptIn(ExperimentalCoilApi::class)
@Composable
fun SampleWorkViewDialog(
    imageUrl: String,
    title: String,
    description: String,
    imageLoader: ImageLoader,
    setShowDialog: (Boolean) -> Unit,
) {
    Dialog(
        onDismissRequest = { setShowDialog(false) },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        (LocalView.current.parent as DialogWindowProvider).window.setDimAmount(1f)
        Column {
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = rememberImagePainter(
                    data = imageUrl,
                    imageLoader = imageLoader
                ),
                contentDescription = description,
                contentScale = ContentScale.FillWidth
            )
            Column(modifier = Modifier.padding(SizeMedium)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                )
                Spacer(modifier = Modifier.height(SizeSmall))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color.White
                    )
                )
            }
        }
    }
}