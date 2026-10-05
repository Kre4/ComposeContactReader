package ru.kre4.contactreader

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ContactList(
    modifier: Modifier = Modifier,
    viewModel: ViewModel = viewModel()
) {
    val context = LocalContext.current
    val contacts = viewModel.contacts

    if (contacts.isEmpty()) {
        Text(
            text = stringResource(R.string.no_contacts),
            textAlign = TextAlign.Center,
            modifier = modifier.fillMaxWidth()
        )
        return
    }
    Toast.makeText(
        context,
        stringResource(R.string.contacts_length, contacts.size),
        Toast.LENGTH_SHORT
    ).show()
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