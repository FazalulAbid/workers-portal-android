package com.fifty.fixitnow.featuresettings.presentation.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.presentation.component.StandardAppBar
import com.fifty.fixitnow.core.presentation.ui.theme.SizeMedium
import com.fifty.fixitnow.core.presentation.ui.theme.SizeSmall
import com.fifty.fixitnow.core.util.Screen
import com.fifty.fixitnow.featuresettings.presentation.component.SettingsButton

@Composable
fun SettingsScreen(
    onNavigateUp: () -> Unit,
    onNavigate: (String) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column() {
            StandardAppBar(
                onNavigateUp = onNavigateUp,
                showBackArrow = true,
                title = {
                    Text(
                        text = stringResource(R.string.settings),
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            )
            LazyColumn(
                contentPadding = PaddingValues(SizeMedium),
                verticalArrangement = Arrangement.spacedBy(SizeMedium)
            ) {
                item {
                    SettingsButton(
                        text = "Terms and Services",
                        painter = painterResource(id = R.drawable.ic_terms)
                    ) {
                        onNavigate(Screen.TermsAndConditionScreen.route)
                    }
                    Spacer(modifier = Modifier.height(SizeSmall))
                    SettingsButton(
                        text = "Privacy and Policy",
                        painter = painterResource(id = R.drawable.ic_privacy)
                    ) {
                        onNavigate(Screen.PrivacyPolicyScreen.route)
                    }
                    Spacer(modifier = Modifier.height(SizeSmall))
                    SettingsButton(
                        text = "Contact Us",
                        painter = painterResource(id = R.drawable.ic_support)
                    ) {
                        onNavigate(Screen.ContactUsScreen.route)
                    }
                }
            }
        }
    }
}