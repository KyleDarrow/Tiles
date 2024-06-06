package com.example.tiles.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tiles.R

// creates the title screen and adds functionality
@Composable
fun TitleScreen(
    onPlay: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize().background(color = Color(0xFF60D3FA))) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val rainbow = remember {
                Brush.sweepGradient(
                    listOf(
                        Color(0xFF9575CD),
                        Color(0xFFBA68C8),
                        Color(0xFFE57373),
                        Color(0xFFFFB74D),
                        Color(0xFFFFF176),
                        Color(0xFFAED581),
                        Color(0xFF4DD0E1),
                        Color(0xFF9575CD)
                    )
                )
            }
            val borderWidth = 4.dp
            Image(
                painter = painterResource(id = R.drawable.final_tiles_cover),
                contentDescription = "tiles cover",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(50.dp).clip(RoundedCornerShape(3.dp))
                    .border(BorderStroke(borderWidth, rainbow))
            )
            // Creates the title.
            Text(
                text = "Tiles",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(6.dp)
            )
            // Description of the game.
            Text(
                text = "Solve for the scrambled words \n and try to reach the gold star!",
                fontSize = 17.sp
            )
            // Adds a play button.
            Button(
                onClick = { onPlay() },
                modifier = Modifier.padding(15.dp),
                colors = ButtonDefaults.buttonColors(Color.Black)
            ) {
                Text(
                    text = "Play",
                    modifier = Modifier.padding(16.dp, 0.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TitleScreenPreview() {
    TitleScreen(
        onPlay = {

        }
    )
}