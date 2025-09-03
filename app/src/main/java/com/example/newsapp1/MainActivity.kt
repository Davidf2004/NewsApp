@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.newsapp1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme(colorScheme = lightColorScheme()) {
                Surface(color = Color.White) { NewsScreen() }
            }
        }
    }
}


data class News(
    val title: String,
    val date: String = "febrero 08 • 2024",
    @DrawableRes val imageRes: Int? = null
)

private val topNews = listOf(
    News("El presidente de EE.UU. no muestra signos de arrepentimiento..."),
    News("Bañarse en la piscina del desierto de Cleopatra")
)

private val worldNews = listOf(
    News(
        "El presidente de EE.UU. no muestra signos de arrepentimiento...",
        imageRes = R.drawable.trump
    ),
    News(
        "Bañarse en la piscina del desierto de Cleopatra",
        imageRes = R.drawable.cleopatra
    ),
    News(
        "Gigantes tecnológicos",
        imageRes = R.drawable.intel
    ),
    News(
        "El rover de Marte envía nuevas imágenes",
        imageRes = R.drawable.marte
    )
)

@Composable
fun NewsScreen() {
    val purple = Color(0xFF6C4DFF)
    val textBlack = Color(0xFF111111)
    val textGray = Color(0xFF9AA0A6)
    val chipGray = Color(0xFFECECEC)

    var query by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(Modifier.height(16.dp))


        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            placeholder = { Text("Buscar", color = textGray) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar", tint = textGray) },
            singleLine = true,
            shape = RoundedCornerShape(28.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .border(1.2.dp, Color(0xFFB0B0B0), RoundedCornerShape(28.dp)),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Spacer(Modifier.height(14.dp))


        Row(modifier = Modifier.fillMaxWidth()) {
            Text("Noticias", color = textBlack, fontWeight = FontWeight.Black, fontSize = 18.sp)
            Spacer(Modifier.width(24.dp))
            Text("Eventos", color = textGray, fontSize = 18.sp)
            Spacer(Modifier.width(24.dp))
            Text("Clima", color = textGray, fontSize = 18.sp)
        }
        Box(
            modifier = Modifier
                .padding(top = 6.dp)
                .width(74.dp)
                .height(4.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(purple)
        )

        Spacer(Modifier.height(14.dp))
        Text("Últimas noticias", color = textBlack, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
        Spacer(Modifier.height(10.dp))

        LazyRow(
            contentPadding = PaddingValues(start = 2.dp, end = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(topNews) { n -> TopNewsCardStyled(n) }
        }

        Spacer(Modifier.height(18.dp))
        Text("Alrededor del mundo", color = textBlack, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
        Spacer(Modifier.height(10.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(worldNews) { n -> WorldNewsCardStyled(n, chipGray, textBlack) }
        }
    }
}



@Composable
fun TopNewsCardStyled(news: News) {
    val purple = Color(0xFF6C4DFF)

    Column(
        modifier = Modifier
            .width(260.dp)
            .height(155.dp)
            .clip(RoundedCornerShape(22.dp))
            .background(purple)
            .padding(16.dp)
    ) {
        Text(
            news.title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = Color.White,
            fontWeight = FontWeight.Black,
            fontSize = 16.sp,
            lineHeight = 20.sp
        )
        Spacer(Modifier.weight(1f))
        Text(
            news.date,
            color = Color.White.copy(alpha = 0.95f),
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun WorldNewsCardStyled(
    news: News,
    chipGray: Color = Color(0xFFECECEC),
    textBlack: Color = Color(0xFF111111)
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {

        if (news.imageRes != null) {
            Image(
                painter = painterResource(id = news.imageRes),
                contentDescription = news.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .matchParentSize()
                    .clip(RoundedCornerShape(16.dp))
            )
        }


        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 10.dp, vertical = 10.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 22.dp, topEnd = 22.dp,
                        bottomStart = 12.dp, bottomEnd = 12.dp
                    )
                )
                .background(chipGray)
                .padding(horizontal = 12.dp, vertical = 10.dp)
                .wrapContentHeight()
                .fillMaxWidth()
        ) {
            Text(
                text = news.title,
                color = textBlack,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                lineHeight = 18.sp
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NewsScreenPreview() {
    MaterialTheme(colorScheme = lightColorScheme()) {
        Surface(color = Color.White) { NewsScreen() }
    }
}
