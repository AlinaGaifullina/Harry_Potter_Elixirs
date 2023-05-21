package ru.itis.harrypotterelixirs.ui.screens.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import ru.itis.harrypotterelixirs.R
import ru.itis.harrypotterelixirs.ui.screens.elixirs.advancedColor
import ru.itis.harrypotterelixirs.ui.screens.elixirs.beginnerColor
import ru.itis.harrypotterelixirs.ui.screens.elixirs.moderateColor
import ru.itis.harrypotterelixirs.ui.screens.elixirs.oneOfAKindColor
import ru.itis.harrypotterelixirs.ui.screens.elixirs.ordinaryWizardingLevelColor
import ru.itis.harrypotterelixirs.ui.screens.elixirs.unknownColor
import ru.itis.harrypotterelixirs.ui.theme.ElixirsTextStyles


@Composable
fun DetailsScreen(
    navController: NavController,
    potionName: String?,
    viewModel: DetailsViewModel = hiltViewModel()
) {

    val detailsState by viewModel.state.collectAsStateWithLifecycle()
    val eventHandler = viewModel::event
    val action by viewModel.action.collectAsStateWithLifecycle(null)

    LaunchedEffect(action) {
        when (action) {
            null -> Unit
            DetailsAction.NavigateBack -> {
                navController.navigateUp()
            }
        }
    }

    val listOfIngredients: MutableList<String> = mutableListOf()
    detailsState.data.elixirIngredients.forEach{
        listOfIngredients.add(it.ingredientName)
    }

    val listOfInventors: MutableList<String> = mutableListOf()
    detailsState.data.elixirInventors.forEach{
        listOfInventors.add(it.inventorName)
    }

    var iconColor = Color.DarkGray
    when(detailsState.data.elixirDifficulty){
        "Unknown" -> iconColor = unknownColor
        "Beginner" -> iconColor = beginnerColor
        "Moderate" -> iconColor = moderateColor
        "OrdinaryWizardingLevel" -> iconColor = ordinaryWizardingLevelColor
        "Advanced" -> iconColor = advancedColor
        "OneOfAKind" -> iconColor = oneOfAKindColor
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        content = {
            item{
                Row(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        modifier = Modifier,
                        onClick = {
                            eventHandler.invoke(DetailsEvent.OnBackBtnClick)
                        }
                    ) {
                        Image(
                            painterResource(R.drawable.ic_back),
                            contentDescription = "icon_back",
                            contentScale = ContentScale.Crop,
                        )
                    }

                    Text(
                        text = potionName ?: "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp),
                        textAlign = TextAlign.Start,
                        style = ElixirsTextStyles.TitleText,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            item {
                Divider(color = MaterialTheme.colorScheme.primary, thickness = 3.dp)
            }

            item {
                if (detailsState.isLoading){
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(32.dp),
                            color = MaterialTheme.colorScheme.primary,
                            strokeWidth = 4.dp
                        )
                        Text(
                            text = stringResource(id = R.string.loading),
                            modifier = Modifier
                                .padding(40.dp),
                            textAlign = TextAlign.Center,
                            style = ElixirsTextStyles.PotionDescription,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                } else {
                if(!detailsState.error.isNullOrEmpty()){
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(id = R.string.loading_error),
                            modifier = Modifier
                                .padding(top = 40.dp),
                            style = ElixirsTextStyles.PotionDescription,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        TextButton(
                            onClick = {
                                eventHandler.invoke(DetailsEvent.LoadElixir(potionName.toString()))
                            },
                            modifier = Modifier.padding(top = 16.dp),
                        ) {
                            Text(
                                text = stringResource(id = R.string.try_again),
                                style = ElixirsTextStyles.ErrorText,
                                color = MaterialTheme.colorScheme.primary,
                            )
                        }
                    }
                } else {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = detailsState.data.elixirDifficulty,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp),
                        textAlign = TextAlign.Center,
                        style = ElixirsTextStyles.SubTitles,
                        color = iconColor
                    )
                    Image(
                        painterResource(R.drawable.ic_potion),
                        contentDescription = "potion_icon",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(color = iconColor)
                    )
                    PotionDescription(
                        subTitle = stringResource(id = R.string.effect),
                        description = detailsState.data.elixirEffect,
                        icon = painterResource(R.drawable.ic_effect)
                    )
                    PotionDescription(
                        subTitle = stringResource(id = R.string.sideEffect),
                        description = detailsState.data.elixirSideEffects,
                        icon = painterResource(R.drawable.ic_side_effects)
                    )
                    PotionDescription(
                        subTitle = stringResource(id = R.string.characteristics),
                        description = detailsState.data.elixirCharacteristics,
                        icon = painterResource(R.drawable.ic_characteristics)
                    )
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(20.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painterResource(R.drawable.ic_ingredients),
                                contentDescription = "potion_icon",
                                contentScale = ContentScale.Crop,
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                                modifier = Modifier
                            )
                            Text(
                                text = stringResource(id = R.string.ingredients),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 12.dp),
                                textAlign = TextAlign.Start,
                                style = ElixirsTextStyles.SubTitles,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        if (listOfIngredients.isEmpty()){
                            Text(
                                text = stringResource(id = R.string.unknown),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 20.dp),
                                textAlign = TextAlign.Start,
                                style = ElixirsTextStyles.PotionDescription,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        } else {
                            listOfIngredients.forEach {
                                Text(
                                    text = it,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 20.dp),
                                    textAlign = TextAlign.Start,
                                    style = ElixirsTextStyles.PotionDescription,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                    }

                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(20.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painterResource(R.drawable.ic_inventors),
                                contentDescription = "potion_icon",
                                contentScale = ContentScale.Crop,
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                                modifier = Modifier
                            )
                            Text(
                                text = stringResource(id = R.string.inventors),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 12.dp),
                                textAlign = TextAlign.Start,
                                style = ElixirsTextStyles.SubTitles,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        if (listOfInventors.isEmpty()){
                            Text(
                                text = stringResource(id = R.string.unknown),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 20.dp),
                                textAlign = TextAlign.Start,
                                style = ElixirsTextStyles.PotionDescription,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        } else {
                            listOfIngredients.forEach {
                                Text(
                                    text = it,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 20.dp),
                                    textAlign = TextAlign.Start,
                                    style = ElixirsTextStyles.PotionDescription,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                    }

                    PotionDescription(
                        subTitle = stringResource(id = R.string.manufacturer),
                        description = detailsState.data.elixirManufacturer,
                        icon = painterResource(R.drawable.ic_manufacturer)
                    )
                }
                }
                }
            }

            item { 
                Spacer(modifier = Modifier.size(40.dp))
            }
        }
    )

}

@Composable
private fun PotionDescription(subTitle: String, description: String, icon: Painter){
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                icon,
                contentDescription = "potion_icon",
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                modifier = Modifier
            )
            Text(
                text = subTitle,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp),
                textAlign = TextAlign.Start,
                style = ElixirsTextStyles.SubTitles,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Text(
            text = description.ifEmpty { stringResource(id = R.string.unknown) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp),
            textAlign = TextAlign.Start,
            style = ElixirsTextStyles.PotionDescription,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}