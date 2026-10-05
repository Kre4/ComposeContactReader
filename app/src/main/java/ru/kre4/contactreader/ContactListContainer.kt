package ru.kre4.contactreader

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.core.content.ContextCompat
import ru.kre4.contactreader.util.fetchAllContacts

@Composable
fun ContactListContainer(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    var isGranted by rememberSaveable {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        isGranted = granted
    }

    LaunchedEffect(Unit) {
        if (!isGranted) {
            permissionLauncher.launch(Manifest.permission.READ_CONTACTS)
        }
    }

    if (!isGranted) {
        Column(
            modifier = modifier
                .fillMaxWidth(),
        ) {
            Text(
                text = "No permission granted. Grant it and restart",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        return
    }

    var contacts by rememberSaveable { mutableStateOf( context.fetchAllContacts()) }

    ContactList(modifier, contacts)
}