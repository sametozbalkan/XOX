package com.example.xox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.xox.ui.theme.XOXTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            XOXTheme {
                XOX()
            }
        }
    }
}

var oyuncu1 = 0
var oyuncu2 = 0

@Composable
fun XOX() {
    val board = remember{ mutableStateOf(Array(3){ arrayOfNulls<String>(3) } )}
    val isBoardFull = board.value.all { row -> row.all { it != null } }
    val oynayan = remember { mutableStateOf("X") }
    val kazanan = remember {
        mutableStateOf<String?>(null)
    }
    val text1 = remember { mutableStateOf("Bergay") }
    val text2 = remember { mutableStateOf("Mıstallı") }
    val resetBoard = Array(3){ arrayOfNulls<String>(3) }
    val resetOyuncu = "X"

    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White)
        .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
            Text(text = "XOX", fontSize = 45.sp, modifier = Modifier.padding(bottom=15.dp))
        }
        Box(modifier = Modifier
            .padding(10.dp)
           ) {
            Column {
                for (row in 0..2){
                    Row {
                        for (col in 0..2){
                            LargeFloatingActionButton(
                                containerColor = Color.LightGray,
                                shape = RectangleShape,
                                onClick = {
                                if (board.value[row][col] == null && kazanan.value == null) {
                                board.value[row][col]=oynayan.value
                                    oynayan.value=if(oynayan.value=="X") "O" else "X"
                                    kazanan.value = kontrol(board.value)
                                }
                            }, modifier = Modifier
                                .padding(4.dp)) {
                                Text(text = board.value[row][col] ?: "", color = Color.Black, fontSize = 28.sp)
                            }
                        }
                    }
                }
            }
        }
        if(kazanan.value == null && !isBoardFull){
            Row (modifier = Modifier
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround){
                    Text(text = "Sıradaki Oyuncu: ${oynayan.value}", Modifier.padding(all = 10.dp))
            }
        }
        if (isBoardFull && kazanan.value == null) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                Button(
                    modifier = Modifier.padding(10.dp),
                    onClick = {
                        board.value = resetBoard
                        oynayan.value = resetOyuncu
                        kazanan.value = null
                    },
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                ) {
                    Text(text = "Yeniden Başla", color = Color.Black)
                }
            }
        }
        if(kazanan.value != null){
            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround){
                Text(text = "Kazanan: ${kazanan.value}", Modifier.padding(10.dp))
            }
            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround){
                Button(modifier = Modifier.padding(5.dp),
                    onClick = {
                    board.value = resetBoard
                    oynayan.value=resetOyuncu
                    kazanan.value=null},
                    shape = RectangleShape, colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                ) {
                    Text(text = "Yeniden Başla", color = Color.Black)
                }
            }
            if (kazanan.value=="X"){
                oyuncu1++
            }
            if (kazanan.value=="O"){
                oyuncu2++
            }
        }
        Row (horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.padding(top = 10.dp)){
            Column (horizontalAlignment = Alignment.CenterHorizontally){
                Text(text = "${text1.value} (X): $oyuncu1", modifier = Modifier.padding(bottom = 10.dp))
                OutlinedTextField(
                    modifier = Modifier.width(150.dp),
                    value = text1.value,
                    onValueChange = {
                        if (it.length<=8){
                            text1.value = it
                        }
                                    },
                    singleLine=true,
                    label = {Text(text = "İlk Oyuncu")}
                    )
            }
            Column (horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(start = 10.dp, end = 10.dp)) {
                Box (modifier = Modifier
                    .height(110.dp)
                    .width(1.dp)
                    .background(Color.Gray)
                    )
            }
            Column (horizontalAlignment = Alignment.CenterHorizontally){
                Text(text = "${text2.value} (O): $oyuncu2", modifier = Modifier.padding(bottom = 10.dp))
                OutlinedTextField(
                    modifier = Modifier.width(150.dp),
                    value = text2.value,
                    onValueChange = {
                        if (it.length<=8){
                            text2.value = it
                        }
                    },
                    singleLine=true,
                    label = {Text(text = "İkinci Oyuncu")}
                )
            }
        }
        Row (horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.padding(top = 20.dp)) {
            Text( "*Powered by Samet Özbalkan :)", fontSize = 12.sp)
        }
        }
}

fun kontrol(board:Array<Array<String?>>): String?{
    for (row in 0..2){
        if(board[row][0] !=null && board[row][0]==board[row][1] && board[row][1]==board[row][2]){
            return board [row][0]
        }
    }
    for (col in 0..2){
        if(board[0][col] !=null && board[0][col]==board[1][col] && board[1][col]==board[2][col]){
            return board [0][col]
        }
    }
    if(board[0][0] !=null && board[0][0]==board[1][1] && board[1][1]==board[2][2]){
        return board [0][0]
    }
    if(board[0][2] !=null && board[0][2]==board[1][1] && board[1][1]==board[2][0]){
        return board [0][2]
    }
    return null
}