package com.example.hellocompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExpandLess
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hellocompose.ui.theme.HelloComposeTheme

private val sampleName = listOf(
    "Joko",
    "Chet",
    "Doris",
    "Eddie",
    "Frank",
    "Greta",
    "Helen",
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HelloComposeTheme {
                HelloJetpackComposeApp()
            }
        }
    }
}

@Composable
fun HelloJetpackComposeApp() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        GreetingList(sampleName)
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun HelloJetpackComposeAppPreview() {
    HelloComposeTheme {
        HelloJetpackComposeApp()
    }
}

@Composable
fun GreetingList(names: List<String>) {
    if (names.isNotEmpty()) {
        Column {
            for (name in names) {
                Greeting(name)
            }
        }
    } else {
        Text("not found")
    }
}

@Composable
fun Greeting(name: String) {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "Logo",
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(text = "Hello $name!", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text(text = "Welcome to Compose!")
        }
        IconButton(onClick = { }) {
            Icon(
                imageVector = if(isExpanded) Icons.Outlined.ExpandLess else Icons.Outlined.ExpandMore,
                contentDescription = if (isExpanded) "Show less" else "Show more"
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    HelloComposeTheme {
//        Greeting("Dave")
//    }
//}