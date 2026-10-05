package ru.kre4.contactreader

import android.content.pm.PackageManager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import ru.kre4.contactreader.util.fetchAllContacts
import android.Manifest
import android.R.style.Theme
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import ru.kre4.contactreader.util.Contact

@Composable
fun ContactList(
    modifier: Modifier = Modifier,
    contacts: List<Contact>) {
    val context = LocalContext.current
//
//    var isGranted by rememberSaveable {
//        mutableStateOf(
//            ContextCompat.checkSelfPermission(
//                context,
//                Manifest.permission.READ_CONTACTS
//            ) == PackageManager.PERMISSION_GRANTED
//        )
//    }
//
//    if (!isGranted) {
//        Text(
//            text = "No permission granted. Grant it and restart",
//            textAlign = TextAlign.Center,
//            modifier = modifier.fillMaxWidth()
//        )
//        val permissionLauncher = rememberLauncherForActivityResult(
//            ActivityResultContracts.RequestPermission()
//        ) { isGranted ->
//            if (isGranted) {
//                // Permission granted, you can now send notifications.
//            } else {
//                // Permission denied, handle accordingly.
//            }
//        }
//
//    }

    if (contacts.isEmpty()) {
        Text(
            text = "No contacts found",
            textAlign = TextAlign.Center,
            modifier = modifier.fillMaxWidth()
        )
        return
    }
    Toast.makeText(context, "Found ${contacts.size} contacts!", Toast.LENGTH_SHORT).show()
    LazyColumn(modifier = modifier) {
        items(contacts) { contact ->
            Row(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable {
                        val intent = Intent(Intent.ACTION_DIAL).apply {
                            data = "tel:${contact.phoneNumber}".toUri()
                        }

                        context.startActivity(intent)
                    },

                ) {
                Column {
                    Text(
                        text = contact.name ?: "",
                        modifier = Modifier.rotate((-3..3).random().toFloat())
                    )
                    Text(
                        text = contact.phoneNumber ?: "",
                        modifier = Modifier.rotate((-3..3).random().toFloat())
                    )
                }

            }
        }
    }
}