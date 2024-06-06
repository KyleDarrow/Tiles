package com.example.tiles.presentation

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tiles.R

// Provides functionality for the game screen.
@Composable
fun GameScreen(viewModel: TilesViewModel, navController: NavController) {
    var category by remember { mutableStateOf<String?>(null) }
    var words by remember { mutableStateOf<List<String>>(emptyList()) }
    var clickableIndex by remember { mutableStateOf(5) }
    var lives by remember { mutableStateOf(3) }
    var gameOver by remember { mutableStateOf(false) }
    var correctAnswers by remember { mutableStateOf(0) }
    var gameWon by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        // get a random category
        val newCategory = viewModel.getRandomCategory()
        category = newCategory
        // get a random list of words based on the category
        if (category != null && words.isEmpty()) {
            words = viewModel.getRandomWords(category!!)
            // log the list information to make sure it is obtained
            Log.d("CheckWordList", "Words fetched: $words")
        }
    }
    Box(
        modifier = Modifier.background(color = Color.LightGray)
    )
     {
         Row(
             modifier = Modifier
                 .fillMaxSize(),
             horizontalArrangement = Arrangement.Center
         ) {
             // Title.
             Text(
                 text = "Tiles",
                 fontSize = 40.sp,
                 fontWeight = FontWeight.Bold,
                 color = Color.White,
                 modifier = Modifier
                     .fillMaxWidth()
                     .background(color = Color(0xFF60A3D9))
                     .padding(horizontal = 160.dp, vertical = 0.dp)
             )
         }
        Column(
            modifier = Modifier.absolutePadding(10.dp, 440.dp, 0.dp, 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Lives section to display attempts remaining.
            Text(
                text = "Lives",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            // when out of lives, end the game
            if (!gameOver) {
                Row {
                    repeat(lives) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Life Icon",
                            tint = Color.Red
                        )
                    }
                }
                if (lives == 0) {
                    gameOver = true
                }
            } else {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "No Lives",
                    tint = Color.Red
                )
                // alert screen indicating game over.
                AlertDialog(
                    onDismissRequest = { gameOver = false },
                    title = {
                        Text(
                            text = "GAME OVER!",
                            modifier = Modifier.padding(65.dp, 0.dp, 0.dp, 0.dp),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = Color.Red
                            )},
                    confirmButton = {
                        Button(
                            onClick = { gameOver = true
                                      navController.navigate("titleScreen")},
                            colors = ButtonDefaults.buttonColors(Color.Red),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                        ) {
                            Text(
                                text = "Try Again",
                                color = Color.White)
                        }
                    },
                )
            }
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Card to display game tiles.
            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 15.dp),
                modifier = Modifier
                    .size(width = 200.dp, height = 490.dp)
                    .padding(0.dp, 60.dp, 0.dp, 10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Black
                ),
            ) {
                LazyColumn {
                    // displays 1 set of 6 flippable cards with front and back content.
                    items(1) { index ->
                        FlipCard(
                            frontContent = { level ->
                                // display specific content based on index
                                when (level) {
                                    5 -> Text(text = "Click to reveal")
                                    4 -> Image(
                                        painter = painterResource(id = R.drawable.bronze_star),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .padding(horizontal = 8.dp)
                                            .fillMaxSize()
                                    )
                                    2 -> Image(
                                        painter = painterResource(id = R.drawable.silver_star),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .padding(horizontal = 8.dp)
                                            .fillMaxSize()
                                    )
                                    0 -> Image(
                                        painter = painterResource(id = R.drawable.gold_star),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .padding(horizontal = 8.dp)
                                            .fillMaxSize()
                                    )
                                }
                            },
                            // scramble the word when the card index is less than or equal to the current clickable card.
                            backContent = { level ->
                                val word = remember(index) { words.getOrNull(level) ?: "" }
                                val wordToShow = if (level <= clickableIndex) scrambleWord(word) else word
                                Text(text = wordToShow)
                            },
                            clickableIndex = clickableIndex
                        )
                    }
                }
            }
            // display the category pulled from the database
            Text(
                text = "Category: ${category ?: ""}",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 10.dp)
            )
            // get the users answer and check to see if it matches the word based on the card that is current clickable.
            var answer by remember { mutableStateOf("") }
            TextField(
                value = answer,
                onValueChange = { userInput ->
                    answer = userInput
                },
                label = { Text("Enter your answer") },
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (answer.lowercase() == words[clickableIndex]) {
                            clickableIndex--
                            answer = ""
                            correctAnswers++
                            if (correctAnswers == 6) {
                                gameWon = true
                            }
                        } else {
                            lives--
                            answer = ""
                        }
                    }
                ),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
            )
            // when the user wins display an alert
            if (gameWon) {
                AlertDialog(
                    onDismissRequest = { gameWon = false },
                    title = {
                        Text(
                            text = "YOU WIN!",
                            modifier = Modifier.padding(85.dp, 0.dp, 0.dp, 0.dp),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = Color(0xFF78BFED)
                        )},
                    confirmButton = {
                        Button(
                            onClick = { gameWon = true
                                navController.navigate("titleScreen")},
                            colors = ButtonDefaults.buttonColors(Color(0xFF78BFED)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                        ) {
                            Text(
                                text = "Play Again?",
                                color = Color.White)
                        }
                    },
                )
            }
        }
         Column(
             modifier = Modifier.absolutePadding(315.dp, 425.dp, 0.dp, 0.dp),
             horizontalAlignment = Alignment.CenterHorizontally
         ) {
             // shuffle the current word in the list with the same index as the clickable index
             // with a different word that from the same category that matches the current word in length.
             ShuffleButton(
                 onClick = {},
             )
         }
     }
}



// Shuffles the current word with another from the database.
@Composable
fun ShuffleButton(onClick: () -> Unit) {
    var shufflesRemaining by remember { mutableStateOf(3) }
    Text(
        text = "($shufflesRemaining Remaining)",
        fontSize = 12.sp
    )
    Button(
        onClick = {
            if (shufflesRemaining > 0){
                shufflesRemaining--
            }
        },
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier.padding(5.dp),
    ) {
        Text(
            "Shuffle",
            fontSize = 10.sp
        )
    }
}



// Colors cards.
fun getColorForLevel(index: Int, clickableIndex: Int): Color {
    val inactiveLevel = listOf(
        Color(0xFFF38274),
        Color(0xFFF3B774),
        Color(0xFFECE8AA),
        Color(0xFFB9ECAA),
        Color(0xFFBDEAE8),
        Color(0xFFE1D3E5),
    )
    val levelColors = listOf(
        Color.Red,
        Color(0xFFFEBE15),
        Color.Yellow,
        Color(0xFF51FE27),
        Color(0xFF3CF6FB),
        Color(0xFFD8B2FC)
    )
    val defaultColors = listOf(
        Color.Red,
        Color(0xFFFEBE15),
        Color.Yellow,
        Color(0xFF51FE27),
        Color(0xFF3CF6FB),
        Color(0xFFD8B2FC)
    )
    // color cards based on the current card active and the previous cards visited.
    return when {
        index == clickableIndex -> levelColors[index % levelColors.size]
        index < clickableIndex -> inactiveLevel[index % inactiveLevel.size]
        else -> defaultColors[index % defaultColors.size]
    }
}

// scramble the words that are retrieved
fun scrambleWord(word: String): String {
    var scrambledWord = word
    while (scrambledWord == word) {
        val brokenWord = word.toCharArray()
        brokenWord.shuffle()
        scrambledWord = String(brokenWord)
    }
    return scrambledWord
}

// Add game cards to the screen with rotation functionality.
@Composable
fun FlipCard(
    modifier: Modifier = Modifier,
    frontContent: @Composable (index: Int) -> Unit,
    backContent: @Composable (index: Int) -> Unit,
    clickableIndex: Int
) {
    repeat(6) { index ->
        var rotated by remember { mutableStateOf(false) }

        val rotation by animateFloatAsState(
            targetValue = if (rotated) 180f else 0f,
            animationSpec = tween(500)
        )

        val animateFront by animateFloatAsState(
            targetValue = if (!rotated) 1f else 0f,
            animationSpec = tween(500), label = ""
        )

        val animateBack by animateFloatAsState(
            targetValue = if (rotated) 1f else 0f,
            animationSpec = tween(500), label = ""
        )

        val isClickable = index == clickableIndex
        OutlinedCard(
            border = BorderStroke(3.dp, Color.White),
            modifier = modifier
                .padding(5.dp, 10.5.dp, 0.dp, 0.dp)
                .size(width = 190.dp, height = 58.dp)
                .clip(RoundedCornerShape(8.dp))
                .graphicsLayer {
                    rotationY = rotation
                    cameraDistance = 8 * density
                }
                .clickable(enabled = isClickable) { rotated = !rotated },
            colors = CardDefaults.cardColors(containerColor = getColorForLevel(index, clickableIndex))
        ) {
            Row(
                modifier = modifier
                    .clickable(enabled = isClickable) { rotated = !rotated }
                    .graphicsLayer {
                        rotationY = rotation
                        alpha = if (rotated) animateBack else animateFront
                    }
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (rotated) {
                        backContent(index)
                    } else {
                        frontContent(index)
                    }
                }
            }
        }
    }
}


