package com.fighter.animatedthemeswitch.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fighter.animatedthemeswitch.ui.theme.CustomTheme
import com.fighter.animatedthemeswitch.ui.theme.darkTheme
import com.fighter.animatedthemeswitch.ui.theme.lightTheme
import com.fighter.animatedthemeswitch.ui.theme.pinkTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MoonTheme(){
    var theme by remember { mutableStateOf(darkTheme) }
    var animationOffset by remember { mutableStateOf(Offset(0f, 0f)) }
    AnimatedContent(
        transitionSpec = {
            fadeIn(
                initialAlpha = 0f,
                animationSpec = tween(100)
            ) with fadeOut(
                targetAlpha = .9f,
                animationSpec = tween(800)
            ) + scaleOut(
                targetScale = .95f,
                animationSpec = tween(800)
            )
        },
        targetState = theme,
        modifier = Modifier.background(Color.Black).fillMaxSize(), label = "",
    ) { currentTheme ->
        val revealSize = remember { Animatable(1f) }
        LaunchedEffect(key1 = "reveal", block = {
            if (animationOffset.x > 0f) {
                revealSize.snapTo(0f)
                revealSize.animateTo(1f, animationSpec = tween(800))
            } else {
                revealSize.snapTo(1f)
            }
        })

        Box(modifier = Modifier.fillMaxSize().clip(CirclePath(revealSize.value, animationOffset))){
            Surface(modifier = Modifier.fillMaxSize(), color = currentTheme.background) {
                Box {
                    Box(modifier = Modifier.fillMaxWidth().height(300.dp)) {
                        Image(
                            modifier = Modifier.fillMaxSize(),
                            painter = painterResource(id = currentTheme.image),
                            contentDescription = "headerImage",
                            contentScale = ContentScale.Crop,
                        )
                        Box(
                            modifier = Modifier.fillMaxSize().background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            currentTheme.background.copy(alpha = .2f),
                                            currentTheme.background
                                        )
                                    )
                                )
                        )
                    }

                    Row(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        ThemeButton(
                            theme = lightTheme,
                            currentTheme = currentTheme,
                            text = "Light",
                        ) {
                            animationOffset = it
                            theme = lightTheme
                        }

                        ThemeButton(
                            theme = darkTheme,
                            currentTheme = currentTheme,
                            text = "Dark",
                        ) {
                            animationOffset = it
                            theme = darkTheme
                        }

                        ThemeButton(
                            theme = pinkTheme,
                            currentTheme = currentTheme,
                            text = "Pink",
                        ) {
                            animationOffset = it
                            theme = pinkTheme
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ThemeButton(
    theme: CustomTheme,
    currentTheme: CustomTheme,
    text: String,
    onClick: (Offset) -> Unit,
) {
    val isSelected = theme == currentTheme
    var offset: Offset = remember { Offset(0f, 0f) }
    Column(horizontalAlignment = Alignment.CenterHorizontally){
        Box(
            modifier = Modifier.onGloballyPositioned {
                    offset = Offset(
                        x = it.positionInWindow().x + it.size.width / 2,
                        y = it.positionInWindow().y + it.size.height / 2
                    )
                }
                .size(110.dp)
                .border(
                    4.dp,
                    color = if (isSelected) theme.primaryColor else Color.Transparent,
                    shape = CircleShape
                )
                .padding(8.dp)
                .background(color = theme.primaryColor, shape = CircleShape)
                .clip(CircleShape)
                .clickable {onClick(offset)}
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = theme.image),
                contentDescription = "themeImage",
                contentScale = ContentScale.Crop,
            )
        }

        Text(
            text = text.uppercase(),
            modifier = Modifier.alpha(if (isSelected) 1f else .5f).padding(2.dp),
            color = currentTheme.textColor,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
    }
}

@Preview
@Composable
fun PreviewMoonTheme() {
    MoonTheme()
}