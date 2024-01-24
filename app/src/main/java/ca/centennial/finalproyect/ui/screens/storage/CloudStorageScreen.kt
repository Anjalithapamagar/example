package ca.centennial.finalproyect.ui.screens.storage

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource

import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import ca.centennial.finalproyect.R

import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import ca.centennial.finalproyect.utils.CloudStorageManager

import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects


@Composable
fun CloudStorageScreen(storage: CloudStorageManager) {
    //Acceso a la camara
    val scope = rememberCoroutineScope()
    //Contexto de la pantalla actual
    val context = LocalContext.current
    val file = context.createImageFile()
    //Get Identifiere to resource in sipositiv
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        "ca.centennial.finalproyect" + ".provider", file
    )
    //Maneja el estado de la camara
    var capturedImageUri by remember { mutableStateOf<Uri>(Uri.EMPTY) }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            Toast.makeText(context, R.string.foto_tomada, Toast.LENGTH_SHORT).show()
            capturedImageUri = uri
            capturedImageUri?.let { uri ->
                scope.launch {
                    storage.uploadFile(file.name, uri)
                }
            }
        } else {
            Toast.makeText(context, "Could not take photo $it", Toast.LENGTH_SHORT).show()
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()) {
        if (it) {
            Toast.makeText(context, R.string.permiso_autorizado, Toast.LENGTH_SHORT).show()
            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(context, R.string.permiso_denegado, Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val permissionCheckResult =
                        ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                        cameraLauncher.launch(uri)
                    } else {
                        //Solicita permiso de la camara al usuario
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                },
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = stringResource(R.string.add_photo))
            }
        }
    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                var gallery by remember { mutableStateOf<List<String>>(listOf()) }
                LaunchedEffect(Unit) {
                    gallery = storage.getUserImages()
                }
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(gallery.size) { index ->
                        val imageUrl = gallery[index]
                        CoilImage(
                            imageUrl = imageUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clickable {
                                    // Crear un AlertDialog con tres botones al hacer clic en la imagen
                                    AlertDialog
                                        .Builder(context)
                                        .setTitle("Image options")
                                        .setPositiveButton("Download") { dialog, which ->
                                            // Lógica para descargar la imagen (si es necesario)
                                        }
                                        .setNeutralButton("Cancelar") { dialog, which ->
                                            dialog.dismiss()
                                        }
                                        .setNegativeButton("Delete $imageUrl") { dialog, which ->
                                            Log.i("imageUrl ", imageUrl)
                                            val imageUrl =
                                                gallery[index] // URL de la imagen a eliminar
                                            Log.i("Foto a eliminar", gallery[index])
                                            val storageManager = CloudStorageManager(context)
                                            val fileName = imageUrl
                                            scope.launch {
                                                try {
                                                    storageManager.deleteFile(fileName)
                                                    gallery = gallery.filterNot { it == imageUrl }
                                                    Log.w("imageUrl ", imageUrl)
                                                    Toast
                                                        .makeText(
                                                            context,
                                                            "Delete Image $imageUrl",
                                                            Toast.LENGTH_SHORT
                                                        )
                                                        .show()
                                                } catch (e: Exception) {
                                                    // Manejar cualquier error que pueda ocurrir al eliminar la imagen
                                                    Toast
                                                        .makeText(
                                                            context,
                                                            R.string.error_deleting_image,
                                                            Toast.LENGTH_SHORT
                                                        )
                                                        .show()
                                                    e.printStackTrace()
                                                }
                                            }
                                        }
                                        .show()
                                },
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CoilImage(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit
) {
    val painter = rememberAsyncImagePainter(
        ImageRequest
            .Builder(LocalContext.current)
            .data(data = imageUrl)
            .apply(block = fun ImageRequest.Builder.() {
                crossfade(true)
                transformations(RoundedCornersTransformation(topLeft = 20f, topRight = 20f, bottomLeft = 20f, bottomRight = 20f))
            })
            .build()
    )

    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier.padding(6.dp),
        contentScale = contentScale,
    )
}

fun Context.createImageFile(): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    return File.createTempFile(
        imageFileName,
        ".jpg",
        externalCacheDir
    )
}
