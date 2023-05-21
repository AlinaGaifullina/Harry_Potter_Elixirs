package ru.itis.harrypotterelixirs.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.itis.harrypotterelixirs.R

private val thin = Font(R.font.montserrat_thin, FontWeight.W100)
private val extraLight = Font(R.font.montserrat_extralight, FontWeight.W200)
private val light = Font(R.font.montserrat_light, FontWeight.W300)
private val regular = Font(R.font.montserrat_regular, FontWeight.W400)
private val medium = Font(R.font.montserrat_medium, FontWeight.W500)
private val semibold = Font(R.font.montserrat_semibold, FontWeight.W600)
private val bold = Font(R.font.montserrat_bold, FontWeight.W700)
private val extraBold = Font(R.font.montserrat_extrabold, FontWeight.W800)
private val black = Font(R.font.montserrat_black, FontWeight.W900)


private val elixirsFontFamily =
    FontFamily(fonts = listOf(thin, extraLight, light, regular, medium, semibold, bold, extraBold, black))

val authorizationMain = TextStyle

val ElixirsTypography = Typography(
    titleLarge = TextStyle(
        fontFamily = elixirsFontFamily,
        fontWeight = FontWeight.W300,
        fontSize = 96.sp,
    )
    //TODO если нужно
)

object ElixirsTextStyles {

    val TitleText = TextStyle(
        fontFamily = elixirsFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
    )

    val SubTitles = TextStyle(
        fontFamily = elixirsFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
    )

    val PotionDescription = TextStyle(
        fontFamily = elixirsFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
    )

    val ErrorText = TextStyle(
        fontFamily = elixirsFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
    )

    val PotionCardTitle = TextStyle(
        fontFamily = elixirsFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
    )
}