package com.fifty.fixitnow.featuresettings.presentation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.presentation.component.StandardAppBar
import com.fifty.fixitnow.featuresettings.presentation.component.WebViewPage

@Composable
fun PrivacyPolicyScreen(
    onNavigateUp: () -> Unit
) {
    val url = "https://fazalulabid.github.io/workers-portal-contact-page/privacy.html"

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StandardAppBar(
            showBackArrow = true,
            title = {
                Text(
                    text = stringResource(R.string.privacy_policy),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            onNavigateUp = onNavigateUp
        )
        WebViewPage(
            modifier = Modifier.fillMaxSize(),
            url = url
        )
    }
}