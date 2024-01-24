package ca.centennial.finalproyect.ui.screens.db

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.centennial.finalproyect.R

import ca.centennial.finalproyect.model.Contact
import ca.centennial.finalproyect.utils.AuthManager
import ca.centennial.finalproyect.utils.RealtimeManager

/*
ES: Esta función @Composable es la pantalla principal que muestra la lista de contactos.
Hace uso del Scaffold para manejar la estructura de la pantalla y del LazyColumn para mostrar
la lista de contactos. Además, incluye lógica para mostrar un mensaje si no hay contactos disponibles.
También muestra un botón flotante (FloatingActionButton) para agregar un nuevo contacto.

EN: This @Composable function is the main screen that displays the contact list.
It uses the Scaffold to manage the screen structure and the LazyColumn to display the contact list.
Additionally, it includes logic to display a message if there are no contacts available.
It also displays a floating button (FloatingActionButton) to add a new contact.
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
//@Preview
@Composable
fun ContactsScreen(realtime: RealtimeManager, authManager: AuthManager) {
    var showAddContactDialog by remember { mutableStateOf(false) }

    val contacts by realtime.getContactsFlow().collectAsState(emptyList())

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showAddContactDialog = true
                },
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = stringResource(R.string.add_contact))
            }

            if (showAddContactDialog) {
                AddContactDialog(
                    onContactAdded = { contact ->
                        realtime.addContact(contact)
                        showAddContactDialog = false
                    },
                    onDialogDismissed = { showAddContactDialog = false },
                    authManager = authManager,
                )
            }
        }
    ) { _  ->
        if(!contacts.isNullOrEmpty()) {
            LazyColumn {
                contacts.forEach { contact ->
                    item {
                        ContactItem(contact = contact, realtime = realtime)
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(imageVector = Icons.Default.Person, contentDescription = null, modifier = Modifier.size(100.dp))
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = stringResource(R.string.no_contacts),
                    fontSize = 18.sp, fontWeight = FontWeight.Thin, textAlign = TextAlign.Center)
            }
        }
    }
}
/*
ES:
@Composable que muestra un elemento de la lista de contactos en forma de tarjeta (Card).
Muestra los detalles del contacto, incluyendo nombre, número de teléfono, correo electrónico, país
y ciudad.
Proporciona un botón de eliminación (IconButton) para eliminar el contacto. Al hacer clic en el
botón, muestra un diálogo de confirmación (DeleteContactDialog) antes de eliminar el contacto.

EN:

@Composable that displays a contact list item in the form of a card.
Shows the contact details, including name, phone number, email, country and city.
Provides a delete button (IconButton) to delete the contact. When you click the button, it displays
a confirmation dialog (DeleteContactDialog) before deleting the contact.
 */
@Composable
fun ContactItem(contact: Contact, realtime: RealtimeManager) {
    var showDeleteContactDialog by remember { mutableStateOf(false) }

    val onDeleteContactConfirmed: () -> Unit = {
        realtime.deleteContact(contact.key ?: "")
    }

    if (showDeleteContactDialog) {
        DeleteContactDialog(
            onConfirmDelete = {
                onDeleteContactConfirmed()
                showDeleteContactDialog = false
            },
            onDismiss = {
                showDeleteContactDialog = false
            }
        )
    }

    Card(
        modifier = Modifier
            .padding(start = 15.dp, end = 15.dp, top = 15.dp, bottom = 0.dp)
            .fillMaxWidth())
    {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(3f)) {
                Text(
                    text = contact.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = contact.phoneNumber,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = contact.email,
                    fontWeight = FontWeight.Thin,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = contact.country,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis)
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = contact.city,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis)

            }
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.Center,
            ) {
                IconButton(
                    onClick = {
                        showDeleteContactDialog = true
                    },
                ) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = stringResource(R.string.delete_icon))
                }
            }
        }
    }
}

//Add new coontact interface
/*
ES
@Composable que muestra un diálogo para agregar un nuevo contacto.
Contiene campos de entrada para nombre, número de teléfono, correo electrónico, país y ciudad.
Al presionar el botón "Agregar", se crea un nuevo objeto Contact y se pasa al callback onContactAdded.
EN:
@Composable showing a dialog to add a new contact.
Contains input fields for name, phone number, email, country and city.
Pressing the "Add" button creates a new Contact object and passes it to the onContactAdded callback.
 */
@Composable
fun AddContactDialog(onContactAdded: (Contact) -> Unit, onDialogDismissed: () -> Unit, authManager: AuthManager) {
    var name by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var uid = authManager.getCurrentUser()?.uid

    AlertDialog(
        onDismissRequest = {},
        title = { Text(text = stringResource(R.string.add_contact)) },
        confirmButton = {
            Button(
                onClick = {
                    val newContact = Contact(
                        name = name,
                        phoneNumber = phoneNumber,
                        email = email,
                        city = city,
                        country = country,
                        uid = uid.toString())
                    onContactAdded(newContact)
                    name = ""
                    phoneNumber = ""
                    email = ""
                }
            ) {
                Text(text = stringResource(R.string.add))
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDialogDismissed()
                }
            ) {
                Text(text = stringResource(R.string.cancelar))
            }
        },
        text = {
            Column {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                    label = { Text(text = stringResource(R.string.name)) }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = phoneNumber.take(16) ,

                    onValueChange = {
                        val formattedNumber = formatPhoneNumber(it)
                        phoneNumber = formattedNumber
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
                    label = { Text(text = stringResource(R.string.phone_format)) }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                    label = { Text(text = stringResource(R.string.email)) }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = country,
                    onValueChange = { country = it },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                    label = { Text(text = stringResource(R.string.country)) }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = city,
                    onValueChange = { city = it },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                    label = { Text(text = stringResource(R.string.city)) }
                )

            }
        }
    )
}
/*
ES
@Composable que muestra un diálogo de confirmación para eliminar un contacto.
Al confirmar la eliminación, llama a la función onConfirmDelete proporcionada.
EN
@Composable showing a confirmation dialog to delete a contact.
When you confirm the deletion, it calls the provided onConfirmDelete function.
 */
@Composable
fun DeleteContactDialog(onConfirmDelete: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.delete_contact)) },
        text = { Text(stringResource(R.string.are_you_sure_you_want_to_delete_the_contact)) },
        confirmButton = {
            Button(
                onClick = onConfirmDelete
            ) {
                Text(stringResource(R.string.accept))
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss
            ) {
                Text(stringResource(R.string.cancelar))
            }
        }
    )
}
// ES : utilidad que formatea el número de teléfono según un formato específico. +1 647 540 5013
// EN : utility that formats the phone number according to a specific format.
fun formatPhoneNumber(input: String): String {
    // Elimina todos los caracteres no numéricos
    val digitsOnly = input.filter { it.isDigit() }

    // Verifica si los dígitos tienen el formato correcto, por ejemplo, +16475405013
    return if (digitsOnly.length >= 10) {
        val countryCode = digitsOnly.substring(0, 1)
        val areaCode = digitsOnly.substring(1, 4)
        val firstPart = digitsOnly.substring(4, 7)
        val secondPart = digitsOnly.substring(7)

        "+$countryCode $areaCode $firstPart $secondPart"
    } else {
        // Si la longitud del número ingresado no es suficiente para un número de teléfono válido, regresa el número sin formato
        input
    }
}