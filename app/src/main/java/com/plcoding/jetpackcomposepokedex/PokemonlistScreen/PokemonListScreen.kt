package com.plcoding.jetpackcomposepokedex.PokemonlistScreen




import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.target.ImageViewTarget
import com.google.accompanist.coil.CoilImage
import com.plcoding.jetpackcomposepokedex.R
import com.plcoding.jetpackcomposepokedex.data.models.PokedexListEntry
import com.plcoding.jetpackcomposepokedex.ui.theme.RobotoCondensed
import timber.log.Timber


@Composable
fun PokemonListScreen(navController: NavController,context: Context){

    Surface(color = MaterialTheme.colors.background,modifier=Modifier.fillMaxSize()) {
     Column {

         Spacer(modifier = Modifier.height(20.dp))
         Image(painter = painterResource(id = R.drawable.ic_international_pok_mon_logo),
             contentDescription ="pokemon",
         modifier = Modifier
             .fillMaxWidth()
             .align(CenterHorizontally))
         Searchbar(hint = "Search...", modifier = Modifier
             .fillMaxWidth()
             .padding(16.dp)){}
         Spacer(modifier = Modifier.height(16.dp))
         Pokemonlist(navController = navController)


     }
}

}

@Composable
fun Searchbar(modifier :Modifier=Modifier,hint:String="",onsearch:(String)->Unit={}){
    var text=remember{
        mutableStateOf("")
    }
    var isHintDisplay by remember {
        mutableStateOf(hint!="")
    }
    Box(modifier = modifier) {
        BasicTextField(value = text.value, onValueChange = {
            onsearch(it)
        }, maxLines = 1, singleLine = true, textStyle = TextStyle(color = Color.Black),
        modifier = Modifier
            .fillMaxWidth()
            .shadow(5.dp, CircleShape)
            .background(Color.White, CircleShape)
            .padding(horizontal = 20.dp, vertical = 12.dp)
            .onFocusChanged { isHintDisplay = it.isFocused != true }

        )
        if (!isHintDisplay){
            Text(text = hint,color=Color.LightGray, modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp))
        }

    }
}
@Composable
fun PokedexEntry(
    entry:PokedexListEntry,
    navController: NavController,
    modifier: Modifier=Modifier,
    viewModel: PokemonListViewModel= hiltViewModel()
){
    val defaulthdomColor=MaterialTheme.colors.surface
    var domColor by remember {
        mutableStateOf(defaulthdomColor)
    }
    Box(  contentAlignment = Alignment.Center, modifier = modifier
        .shadow(5.dp, RoundedCornerShape(10.dp))
        .clip(RoundedCornerShape(10.dp))
        .aspectRatio(1f)
        .background(Brush.verticalGradient(listOf(domColor, defaulthdomColor)))
        .clickable {
            navController.navigate("pokemon_detail_screen/${domColor.toArgb()}/${entry.pokemonName}")
        }
    )
    {
        Timber.d("a")
Column{
    Timber.d("b")

    CoilImage(request = ImageRequest.Builder(LocalContext.current)
        .data(entry.imageUrl)
        .target{

            viewModel.calculateDominantColor(it){
                domColor=it
            }
        }
        .build(),
        contentDescription = entry.pokemonName,
        fadeIn = true,
        modifier = Modifier
            .size(120.dp)
            .align(CenterHorizontally)

    )
    {
        Timber.d("c")

        CircularProgressIndicator(color= MaterialTheme.colors.primary , modifier = Modifier.scale(0.5f))
    }


}
    Text(text = entry.pokemonName, fontFamily = RobotoCondensed,
        fontSize = 20.sp, textAlign = TextAlign.Center, modifier = Modifier.fillMaxSize())
}
    }


@Composable
fun Pokemonlist(
    navController: NavController,
    viewModel: PokemonListViewModel= hiltViewModel()
){
    val pokemonList by remember {viewModel.pokemonList }
    val enReached by remember {viewModel.endReached }
    val loadError by remember {viewModel.loadError }
    val isloading by remember {viewModel.isloading }

    LazyColumn(contentPadding = PaddingValues(16.dp)){
        val itemcount=if(pokemonList.size % 2==0){
            pokemonList.size/2
        }else{
            pokemonList.size /2 +1
        }
        items(itemcount){
            if (it>=itemcount-1&& !enReached){
                viewModel.loadPokemonPaginated()
            }
            PokedexRow(rowIndex = it, entries = pokemonList, navController =navController )
        }
    }

}

@Composable
fun PokedexRow(
    rowIndex:Int,
    entries:List<PokedexListEntry>,
    navController: NavController
){
    Column {
        Row {
           PokedexEntry(entry = entries.get(rowIndex*2),
               navController = navController, 
               modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.width(16.dp))
            if (entries.size>=rowIndex*2+2){
                PokedexEntry(entry = entries.get(rowIndex*2+1),
                    navController = navController,
                    modifier = Modifier.weight(1f))

            }else{
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}
