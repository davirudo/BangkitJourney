package com.example.coffeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.coffeapp.model.*
import com.example.coffeapp.ui.components.CategoryItem
import com.example.coffeapp.ui.components.SearchBar
import com.example.coffeapp.ui.components.SectionText
import com.example.coffeapp.ui.theme.CoffeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoffeAppTheme {
                CoffeeApp()
            }
        }
    }
}

@Composable
fun CoffeeApp(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Banner()
        SectionText(stringResource(R.string.section_category))
        CategoryRow()
    }
}

@Composable
fun Banner(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Image(
            painter = painterResource(R.drawable.banner),
            contentDescription = "Banner Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.height(160.dp)
        )
        SearchBar()
    }
}

@Composable
fun CategoryRow(
    modifier: Modifier = Modifier
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier.padding(16.dp)
    ) {
        items(dummyCategory, key = { it.textCategory }) { category ->
            CategoryItem(category)
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun DefaultPreview() {
    CoffeAppTheme {
            CoffeeApp()
    }
}