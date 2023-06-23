package com.fifty.workersportal.featureworker.presentation.postsamplework

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.fifty.workersportal.R
import com.fifty.workersportal.core.presentation.component.SecondaryHeader
import com.fifty.workersportal.core.presentation.component.StandardAppBar
import com.fifty.workersportal.core.presentation.component.StandardBottomSheet
import com.fifty.workersportal.core.presentation.ui.theme.SizeLarge
import com.fifty.workersportal.core.presentation.ui.theme.SizeMedium
import com.fifty.workersportal.featureworker.presentation.component.SelectImageSourceButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostSampleWorkScreen(
    onNavigateUp: () -> Unit,
    viewModel: PostSampleWorkViewModel = hiltViewModel()
) {
    var showImageSourceSelectorSheet by remember { mutableStateOf(false) }

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StandardAppBar(
            onNavigateUp = onNavigateUp,
            showBackArrow = true,
            title = {
                Text(
                    text = stringResource(R.string.post_your_work),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        )
        Button(onClick = {
            showImageSourceSelectorSheet = true
        }) {

        }
    }

    if (showImageSourceSelectorSheet) {
        StandardBottomSheet(
            sheetState = rememberModalBottomSheetState(),
            onDismiss = {
                showImageSourceSelectorSheet = false
            }
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(SizeMedium)
            ) {
                SecondaryHeader(text = stringResource(R.string.select_image_source))
                Spacer(modifier = Modifier.height(SizeLarge))
                Row(
                    Modifier
                        .fillMaxWidth()
                ) {
                    SelectImageSourceButton(
                        icon = painterResource(id = R.drawable.ic_camera),
                        text = stringResource(id = R.string.camera),
                        containerColor = MaterialTheme.colorScheme.primary,
                        onClick = {}
                    )
                    Spacer(modifier = Modifier.width(SizeLarge))
                    SelectImageSourceButton(
                        icon = painterResource(id = R.drawable.ic_gallery),
                        text = stringResource(id = R.string.gallery),
                        containerColor = MaterialTheme.colorScheme.secondary,
                        onClick = {}
                    )
                }
                Spacer(modifier = Modifier.height(SizeLarge))
            }
        }
    }
}