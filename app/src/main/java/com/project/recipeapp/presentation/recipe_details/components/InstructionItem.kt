package com.project.recipeapp.presentation.recipe_details.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.project.recipeapp.domain.Instruction

@Composable
fun InstructionItem(instruction: Instruction){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp),
    ){
        Text(
            text = "${instruction.step}. ${instruction.description}",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(vertical = 5.dp)
        )
    }
}