package com.devaloteam.dictionary.feature_dictionary.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devaloteam.dictionary.feature_dictionary.domain.model.WordInfo

@Composable
fun WordInfoItem(
    wordInfo: WordInfo,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = wordInfo.word,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        wordInfo.phonetic?.let { phonetic ->
            Text(text = phonetic, fontWeight = FontWeight.Light)
        }
        Spacer(modifier = Modifier.height(16.dp))
        wordInfo.origin?.let { origin ->
            Text(text = origin)
        }

        wordInfo.meanings.forEach { meaning ->
            Text(text = meaning.partOfSpeech ?: "", fontWeight = FontWeight.Bold, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(16.dp))
            meaning.definitions.forEachIndexed { i, definition ->
                Row {
                    Text(text = "${i + 1}", fontStyle = FontStyle.Italic)
                    Text(text = ". ${definition.definition}")
                }
                definition.example?.let { example ->
                    Row {
                        Text(text = "Ejemplo:", fontWeight = FontWeight.Bold)
                        Text(text = " $example")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}