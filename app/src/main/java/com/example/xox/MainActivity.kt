package com.example.xox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.xox.ui.theme.XOXTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            XOXTheme {
                PageNavigations()
            }
        }
    }
}

@Composable
fun PageNavigations() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "MainScreen") {
        composable("MainScreen") {
            MainScreen(navController = navController)
        }
        composable("GameScreen") {
            XOX()
        }
    }

}

@Composable
fun MainScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "XOX", Modifier.padding(bottom = 40.dp), fontSize = 60.sp)
        Box(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "X0X oyunu, iki kişilik veya bilgisayara karşı oynanabilen bir oyundur. Bir taraf X harfini bir taraf O harfini almaktadır. İki taraf da taşlarını sırayla karelere koyarak kendi taşlarından üç tanesini aynı hizaya koymaya çalışmaktadır. Oyunu kazanmak için yatay, dikey veya çapraz olarak X veya O harfinin üç tanesi birleşmelidir.",
                textAlign = TextAlign.Center
            )
        }
        Button(onClick = {
            navController.navigate("GameScreen")
        }, modifier = Modifier.padding(top = 30.dp)) {
            Text(text = "Oyuna Başla")
        }
    }
}