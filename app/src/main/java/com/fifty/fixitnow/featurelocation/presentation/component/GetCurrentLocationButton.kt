package com.fifty.fixitnow.featurelocation.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.fifty.fixitnow.R
import com.fifty.fixitnow.core.presentation.ui.theme.MediumStrokeThickness
import com.fifty.fixitnow.core.presentation.ui.theme.SizeSmall

@Composable
fun GetCurrentLocationButton(
    text: String,
    onClick: () -> Unit
) {
    OutlinedButton(
        border = BorderStroke(
            MediumStrokeThickness,
            MaterialTheme.colorScheme.primary
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.primary
        ),
        shape = MaterialTheme.shapes.medium,
        onClick = onClick
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_current_location),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(SizeSmall))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}