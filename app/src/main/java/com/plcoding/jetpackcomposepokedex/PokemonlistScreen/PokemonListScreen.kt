package com.plcoding.jetpackcomposepokedex.PokemonlistScreen




import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
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
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.google.accompanist.coil.CoilImage
import com.plcoding.jetpackcomposepokedex.R
import com.plcoding.jetpackcomposepokedex.data.models.PokedexListEntry
import com.plcoding.jetpackcomposepokedex.ui.theme.RobotoCondensed
import timber.log.Timber
import java.nio.file.WatchEvent


@Composable
fun PokemonListScreen(navController: NavController,viewModel: PokemonListViewModel= hiltViewModel()){

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
             .padding(16.dp)){
             viewModel.searchPokemon(it)
         }
         Spacer(modifier = Modifier.height(16.dp))
         Pokemonlist(navController = navController)


     }
}

}

@Composable
fun Searchbar(modifier :Modifier=Modifier,hint:String="",onsearch:(String)->Unit={}){
    var text by remember{
        mutableStateOf("")
    }
    var isHintDisplay by remember {
        mutableStateOf(hint!="")
    }
    Box(modifier = modifier) {
        BasicTextField(value = text, onValueChange = {
            text=it
            onsearch(it)
        }, maxLines = 1, singleLine = true, textStyle = TextStyle(color = Color.Black),
        modifier = Modifier
            .fillMaxWidth()
            .shadow(5.dp, CircleShape)
            .background(Color.White, CircleShape)
            .padding(horizontal = 20.dp, vertical = 12.dp)
            .onFocusChanged { isHintDisplay = (it.isFocused ==false) && (text.isEmpty()) }

        )
        if (isHintDisplay){
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

Column{
    val painter = rememberImagePainter(
        data = entry.imageUrl
    )
Box(modifier = Modifier.fillMaxWidth()){
    (painter.state as? ImagePainter.State.Success)?.let { successState ->

        LaunchedEffect(Unit) {
                val drawable = successState.result.drawable
                viewModel.calculateDominantColor(drawable) { color ->
                    domColor = color
                }
            }
        }

    val painterState = painter.state
    Image(
        painter = painter,
        contentDescription = entry.pokemonName,
        modifier = Modifier
            .size(128.dp)
            .align(Center),
    )
    if (painterState is ImagePainter.State.Loading){
        CircularProgressIndicator(
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .scale(.5f)
                .align(Center)
        )
    }}


    Text(
        text = entry.pokemonName,
        fontSize = 20.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )

}}}



@Composable
fun Pokemonlist(
    navController: NavController,
    viewModel: PokemonListViewModel= hiltViewModel()
){
    val pokemonList by remember {viewModel.pokemonList }
    val enReached by remember {viewModel.endReached }
    val loadError by remember {viewModel.loadError }
    val isloading by remember {viewModel.isloading }
    val isSearching by remember{viewModel.isSearching}
    LazyColumn(contentPadding = PaddingValues(16.dp)){
        val itemcount=if(pokemonList.size % 2==0){
            pokemonList.size/2
        }else{
            pokemonList.size /2 +1
        }
        items(itemcount){
            Timber.d(it.toString())
            if (it>=itemcount-1&& !enReached&& !isloading&&!isSearching){
                viewModel.loadPokemonPaginated()
            }
            PokedexRow(rowIndex = it, entries = pokemonList, navController =navController )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Center){
        if (isloading){
            CircularProgressIndicator(color = MaterialTheme.colors.primary)
            
        }
        if(loadError.isNotEmpty()) {
            RetrySection(error = loadError) {
                viewModel.loadPokemonPaginated()
            }
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
@Composable
fun RetrySection(
    error:String,
    onRetry:()->Unit
){
    Column {
        Text(text = error, color = Color.Red, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { onRetry() }, modifier = Modifier.align(CenterHorizontally)) {
            Text(text = "Retry")
        }
    }
}

