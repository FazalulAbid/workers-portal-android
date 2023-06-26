package com.fifty.workersportal.featureworker.presentation.registerasworker

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.component.SecondaryHeader
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.core.presentation.ui.theme.SmallStrokeThickness
import com.fifty.workersportal.core.presentation.util.CropActivityResultContract
import com.fifty.workersportal.core.presentation.util.shimmerEffect

@OptIn(ExperimentalCoilApi::class)
@Composable
fun IdentitySection(
    viewModel: RegisterAsWorkerViewModel,
    imageLoader: ImageLoader
) {

    val cropIdentityPictureLauncher = rememberLauncherForActivityResult(
        contract = CropActivityResultContract()
    ) {
        viewModel.onEvent(RegisterAsWorkerEvent.CropIdentityPicture(it))
    }
    val identityGalleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) {
        if (it == null) return@rememberLauncherForActivityResult
        cropIdentityPictureLauncher.launch(it)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        if (viewModel.identityImageUri.value != null ||
            viewModel.updateWorkerState.value.profile?.identityUrl?.isNotBlank() == true
        ) {
            Image(
                painter = rememberImagePainter(
                    data = viewModel.identityImageUri.value
                        ?: viewModel.updateWorkerState.value.profile?.identityUrl,
                    imageLoader = imageLoader
                ),
                contentDescription = null,
                Modifier
                    .padding(SizeMedium)
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .clickable {
                        identityGalleryLauncher.launch("image/*")
                    }
                    .shimmerEffect(viewModel.updateWorkerState.value.isLoading),
                contentScale = ContentScale.Crop
            )
        } else {
            Box(
                modifier = Modifier
                    .padding(SizeMedium)
                    .clip(MaterialTheme.shapes.medium)
                    .clickable {
                        identityGalleryLauncher.launch("image/*")
                    }
                    .fillMaxWidth()
                    .aspectRatio(2.77f)
                    .border(
                        SmallStrokeThickness,
                        color = MaterialTheme.colorScheme.outline,
                        shape = MaterialTheme.shapes.medium
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.padding(SizeMedium),
                    text = stringResource(R.string.click_to_upload_you_identity_document),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }
    }
}