package com.fifty.workersportal.core.presentation.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PermissionDialog(
    permissionTextProvider: PermissionTextProvider,
    isPermanentlyDeclined: Boolean,
    onDismiss: () -> Unit,
    onOkClick: () -> Unit,
    onGotoAppSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                if (isPermanentlyDeclined) {
                    onGotoAppSettingsClick()
                } else {
                    onOkClick()
                }
            }) {
                Text(
                    text = if (isPermanentlyDeclined) {
                        "Grant permission"
                    } else "Ok"
                )
            }
        },
        title = {
            Text(text = "Permission required")
        },
        text = {
            Text(
                text = permissionTextProvider.getDescription(
                    isPermanentlyDeclined = isPermanentlyDeclined
                )
            )
        },
        modifier = modifier
    )
}

interface PermissionTextProvider {
    fun getDescription(isPermanentlyDeclined: Boolean): String
}

class CameraPermissionTextProvider() : PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined) {
            "It seems you permanently declined camera permission. " +
                    "You can go to the app settings to grant it."
        } else {
            "This app need access to your camera, so that you can add " +
                    "your sample works and other images to your profile."
        }
    }
}

class CoarseLocationPermissionTextProvider : PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined) {
            "It seems you permanently declined location permission. " +
                    "You can go to the app settings to grant it."
        } else {
            "This app needs access to your location in order to match " +
                    "you with available workers nearby. Also save your " +
                    "addresses for better user experience."
        }
    }
}
