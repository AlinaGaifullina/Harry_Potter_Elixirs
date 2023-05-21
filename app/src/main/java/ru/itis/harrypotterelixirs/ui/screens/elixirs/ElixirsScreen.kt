package ru.itis.harrypotterelixirs.ui.screens.elixirs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import ru.itis.harrypotterelixirs.R
import ru.itis.harrypotterelixirs.ui.model.ElixirDataModel
import ru.itis.harrypotterelixirs.ui.navigation.graphs.MainScreen
import ru.itis.harrypotterelixirs.ui.theme.ElixirsTextStyles

val unknownColor = Color(0xFFBEBEBE)
val beginnerColor = Color(0xFF8BC34A)
val moderateColor = Color(0xFF009688)
val ordinaryWizardingLevelColor = Color(0xFF03A9F4)
val advancedColor = Color(0xFF673AB7)
val oneOfAKindColor = Color(0xFFE91E63)

@Composable
fun ElixirsScreen(
    navController: NavController,
    viewModel: ElixirsViewModel = hiltViewModel(),
) {
    val elixirsState by viewModel.state.collectAsStateWithLifecycle()
    val eventHandler = viewModel::event
    
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        content = {
            item{
                Text(
                    text = stringResource(id = R.string.potions),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    textAlign = TextAlign.Center,
                    style = ElixirsTextStyles.TitleText,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            item{
                Divider(color = MaterialTheme.colorScheme.primary, thickness = 3.dp)
            }

            item {
                Column(
                ){
                    Text(
                        modifier = Modifier
                            .padding(top = 16.dp, start = 20.dp),
                        text = stringResource(id = R.string.difficulty),
                        style = ElixirsTextStyles.SubTitles,
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    DifficultyRow(color = unknownColor, text = stringResource(id = R.string.unknown))
                    DifficultyRow(color = beginnerColor, text = stringResource(id = R.string.beginner))
                    DifficultyRow(color = moderateColor, text = stringResource(id = R.string.moderate))
                    DifficultyRow(color = ordinaryWizardingLevelColor, text = stringResource(id = R.string.ordinary_wizarding_level))
                    DifficultyRow(color = advancedColor, text = stringResource(id = R.string.advanced))
                    DifficultyRow(color = oneOfAKindColor, text = stringResource(id = R.string.one_of_a_kind))
                }
            }

            item {
                if (elixirsState.isLoading) {
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
                }
            }

            item {
                if(!elixirsState.error.isNullOrEmpty()){
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
                                eventHandler.invoke(ElixirsEvent.LoadElixirs)
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
                }
            }

            items(elixirsState.data.size){

                var iconColor = Color.DarkGray
                when(elixirsState.data[it].elixirDifficulty){
                    "Unknown" -> iconColor = unknownColor
                    "Beginner" -> iconColor = beginnerColor
                    "Moderate" -> iconColor = moderateColor
                    "OrdinaryWizardingLevel" -> iconColor = ordinaryWizardingLevelColor
                    "Advanced" -> iconColor = advancedColor
                    "OneOfAKind" -> iconColor = oneOfAKindColor
                }

                CreateListItem(
                    item = elixirsState.data[it],
                    eventHandler = eventHandler,
                    navController = navController,
                    iconColor = iconColor
                )
            }
    }
    )

}
@Composable
fun DifficultyRow(
    color: Color,
    text: String
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(start = 20.dp, top = 12.dp, bottom = 12.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .size(16.dp)
                .background(color)
        )
        Text(
            text = text,
            modifier = Modifier
                .padding(start = 20.dp),
            textAlign = TextAlign.Center,
            style = ElixirsTextStyles.PotionDescription,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun CreateListItem(
    item: ElixirDataModel,
    eventHandler: (ElixirsEvent) -> Unit,
    navController: NavController,
    iconColor: Color
) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 20.dp, end = 20.dp, top = 12.dp, bottom = 12.dp)
        .clickable {
            navController.navigate(MainScreen.Details.passPotionName(item.elixirName))
        },
        elevation = CardDefaults.cardElevation(10.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painterResource(R.drawable.ic_potion),
                contentDescription = "potion_icon",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .background(color = iconColor)
                    .padding(16.dp)
                    .size(56.dp)
            )
            Text(
                text = item.elixirName,
                modifier = Modifier.padding(start = 20.dp, end = 16.dp),
                style = ElixirsTextStyles.PotionCardTitle,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }

}