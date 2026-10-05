package ru.kre4.contactreader

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import ru.kre4.contactreader.util.Contact
import ru.kre4.contactreader.util.fetchAllContacts

/**
 * Решил через ViewModel, потому что столкнулся с проблемами при засовывании листа в rememberSaveable
 */
class ViewModel(application: Application) : AndroidViewModel(application) {
    var contacts by mutableStateOf(emptyList<Contact>())
        private set


    init {
        contacts = application.fetchAllContacts()
    }
}