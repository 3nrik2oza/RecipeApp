package com.project.recipeapp.presentation.recipe_details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.project.recipeapp.ui.theme.RecipeAppTheme

@Composable
fun MessageAlertDialog(
    onDismiss: () -> Unit,
    message: String = "This is the message",
){
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {},
        modifier = Modifier,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ){
                Icon(imageVector = Icons.Default.Info, contentDescription = "")
                Text(
                    text = "WARNING",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        },
        text = {
            Text(
                text = message,

            )
        },
        dismissButton = {
            Button(
                onClick = onDismiss
            ) {
                Text(
                    text = "Close"
                )
            }
        }
    )
}

@Preview
@Composable
fun MessageDialogPrev(){
    RecipeAppTheme {
        MessageAlertDialog(
            onDismiss = {},
            )
    }

}