package com.insurtech.kanguro.shared.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import kanguro.shared.generated.resources.Res
import kanguro.shared.generated.resources.lato
import kanguro.shared.generated.resources.museo_sans

@Composable
fun getMuseoSansFontFamily(): FontFamily = FontFamily(org.jetbrains.compose.resources.Font(Res.font.museo_sans))

@Composable
fun getLatoFontFamily(): FontFamily = FontFamily(org.jetbrains.compose.resources.Font(Res.font.lato))

@Composable
fun BKLHeading3() = TextStyle(
    fontFamily = getMuseoSansFontFamily(),
    fontWeight = FontWeight.SemiBold,
    color = SecondaryDarkest,
    lineHeight = 37.5.sp,
    fontSize = 31.25.sp
)

@Composable
fun MSansSemiBoldSecondaryDarkest21() = TextStyle(
    fontFamily = getMuseoSansFontFamily(),
    fontWeight = FontWeight.SemiBold,
    color = SecondaryDarkest,
    fontSize = 21.sp,
    lineHeight = 25.2.sp
)

@Composable
fun LatoBoldSecondaryDarkest16() = TextStyle(
    fontFamily = getLatoFontFamily(),
    fontWeight = FontWeight.Bold,
    color = SecondaryDarkest,
    fontSize = 16.sp
)

@Composable
fun MobaBodyRegular() = TextStyle(
    fontFamily = getLatoFontFamily(),
    fontWeight = FontWeight.W400,
    color = SecondaryDarkest,
    lineHeight = 19.2.sp,
    fontSize = 16.sp
)

@Composable
fun MobaCaptionRegular() = TextStyle(
    fontFamily = getLatoFontFamily(),
    fontWeight = FontWeight.W400,
    color = SecondaryDarkest,
    lineHeight = 12.sp,
    fontSize = 12.sp
)

@Composable
fun MobaTitle1() = TextStyle(
    fontFamily = getMuseoSansFontFamily(),
    fontWeight = FontWeight.SemiBold,
    color = SecondaryDarkest,
    lineHeight = 38.4.sp,
    fontSize = 32.sp
)

@Composable
fun MobaTitle3() = TextStyle(
    fontFamily = getMuseoSansFontFamily(),
    fontWeight = FontWeight.Normal,
    color = SecondaryDarkest,
    lineHeight = 28.8.sp,
    fontSize = 24.sp
)

@Composable
fun MobaTitle3Light() = MobaTitle3().copy(fontWeight = FontWeight.Light)

@Composable
fun MobaTitle3SemiBold() = MobaTitle3().copy(fontWeight = FontWeight.SemiBold)

@Composable
fun Heading6ExtraBold() = TextStyle(
    fontFamily = getLatoFontFamily(),
    fontWeight = FontWeight.ExtraBold,
    color = SecondaryDarkest,
    lineHeight = 19.2.sp,
    fontSize = 16.sp
)

@Composable
fun MobaFootnoteRegular() = TextStyle(
    fontFamily = getLatoFontFamily(),
    fontWeight = FontWeight.W400,
    color = SecondaryLight,
    lineHeight = 11.sp,
    fontSize = 11.sp
)

@Composable
fun MobaSubheadRegular() = TextStyle(
    fontFamily = getLatoFontFamily(),
    fontWeight = FontWeight.W400,
    color = SecondaryDarkest,
    lineHeight = 16.8.sp,
    fontSize = 14.sp
)

@Composable
fun MobaSubheadBlack() = MobaSubheadRegular().copy(fontWeight = FontWeight.Black)

@Composable
fun MobaHeadline() = TextStyle(
    fontFamily = getMuseoSansFontFamily(),
    fontWeight = FontWeight.SemiBold,
    color = SecondaryDarkest,
    lineHeight = 25.2.sp,
    fontSize = 21.sp
)

@Composable
fun BKSParagraphRegular() = TextStyle(
    fontFamily = getLatoFontFamily(),
    fontWeight = FontWeight.W400,
    color = SecondaryMedium,
    lineHeight = 15.6.sp,
    fontSize = 13.sp
)

@Composable
fun ClickableTextStyle() = TextStyle(
    fontFamily = getLatoFontFamily(),
    fontWeight = FontWeight.Bold,
    fontStyle = FontStyle.Italic,
    color = TertiaryDarkest,
    lineHeight = 15.6.sp,
    fontSize = 13.sp,
    textDecoration = TextDecoration.Underline
)

@Composable
fun ClickableTextStyleNormal() = TextStyle(
    fontFamily = getLatoFontFamily(),
    fontWeight = FontWeight.Bold,
    color = TertiaryExtraDark,
    lineHeight = 11.sp,
    fontSize = 11.sp,
    textDecoration = TextDecoration.Underline
)

@Composable
fun MSansSemiBoldSecondaryDarkest25() = TextStyle(
    fontFamily = getMuseoSansFontFamily(),
    fontWeight = FontWeight.SemiBold,
    color = SecondaryDarkest,
    fontSize = 25.sp
)

@Composable
fun LatoBoldNeutralMedium2Size10() = TextStyle(
    fontSize = 10.sp,
    fontFamily = getLatoFontFamily(),
    fontWeight = FontWeight.Bold,
    color = NeutralMedium2
)

@Composable
fun SmallRegular() = TextStyle(
    fontSize = 12.8.sp,
    lineHeight = 15.36.sp,
    fontFamily = getLatoFontFamily(),
    fontWeight = FontWeight.Normal,
    color = NavyBlue
)

@Composable
fun MobaSubheadLight() = TextStyle(
    fontFamily = getMuseoSansFontFamily(),
    fontWeight = FontWeight.Light,
    color = SecondaryDark,
    lineHeight = 16.8.sp,
    fontSize = 14.sp
)

@Composable
fun LatoRegularSecondaryDarkSize12() = TextStyle(
    fontSize = 12.sp,
    lineHeight = 14.4.sp,
    fontFamily = getLatoFontFamily(),
    fontWeight = FontWeight.W400,
    color = SecondaryDark
)

@Composable
fun TextButtonStyle() = TextStyle(
    fontFamily = getLatoFontFamily(),
    fontWeight = FontWeight.ExtraBold,
    lineHeight = 19.2.sp,
    fontSize = 16.sp
)

@Composable
fun BKSHeading4() = TextStyle(
    fontFamily = getMuseoSansFontFamily(),
    fontWeight = FontWeight.SemiBold,
    color = SecondaryDark,
    lineHeight = 24.37.sp,
    fontSize = 20.31.sp,
    textAlign = TextAlign.Center
)

@Composable
fun LatoBoldNeutral() = TextStyle(
    fontSize = 10.4.sp,
    lineHeight = 12.48.sp,
    fontFamily = getLatoFontFamily(),
    fontWeight = FontWeight.Bold,
    color = NeutralMedium2
)

@Composable
fun MuseoSans15RegularSecondaryDark() = TextStyle(
    fontSize = 15.sp,
    lineHeight = 18.sp,
    fontFamily = getMuseoSansFontFamily(),
    fontWeight = FontWeight.Normal,
    color = SecondaryDark,
    textAlign = TextAlign.Center
)

// Additional derived styles
@Composable
fun MobaBodyBold() = MobaBodyRegular().copy(fontWeight = FontWeight.Bold)

@Composable
fun MobaBodyItalic() = MobaBodyRegular().copy(fontStyle = FontStyle.Italic)

@Composable
fun MobaCaptionBold() = MobaCaptionRegular().copy(fontWeight = FontWeight.Bold)

@Composable
fun MobaFootnoteBlack() = MobaFootnoteRegular().copy(fontWeight = FontWeight.Black)

@Composable
fun BKSParagraphBlack() = BKSParagraphRegular().copy(fontWeight = FontWeight.Black, color = SecondaryDarkest)

@Composable
fun BKSParagraphBold() = BKSParagraphRegular().copy(fontWeight = FontWeight.Bold, color = SecondaryDarkest)

@Composable
fun BKSParagraphRegularSDarkest() = BKSParagraphRegular().copy(color = SecondaryDarkest)

@Composable
fun MobaCaptionRegularSDark() = MobaCaptionRegular().copy(color = SecondaryDark)

@Composable
fun MobaBodyRegularSDark() = MobaBodyRegular().copy(color = SecondaryDark)

@Composable
fun MobaBodyRegularTD() = MobaBodyRegular().copy(color = TertiaryDarkest)

@Composable
fun MobaSubheadBold() = MobaSubheadRegular().copy(fontWeight = FontWeight.Bold)

@Composable
fun MobaSubheadBoldSecondaryMedium() = MobaSubheadBold().copy(color = SecondaryMedium)

@Composable
fun MobaCaptionRegularNeutralLightest() = MobaCaptionRegular().copy(color = NeutralLightest)

@Composable
fun MobaBodyRegularNeutralLightest() = MobaBodyRegular().copy(color = NeutralLightest)

@Composable
fun MobaSubheadRegularNegativeMedium() = MobaSubheadRegular().copy(color = NegativeMedium)

@Composable
fun MobaTitle1PrimaryDarkest() = MobaTitle1().copy(color = PrimaryDarkest)

@Composable
fun MobaSubheadRegularSecondaryDark() = MobaSubheadRegular().copy(color = SecondaryDark)

@Composable
fun SmallRegularBold() = SmallRegular().copy(fontWeight = FontWeight.Bold, color = SecondaryDark)

@Composable
fun MobaSubheadRegularSecondaryMedium() = MobaSubheadRegular().copy(color = SecondaryMedium)

@Composable
fun MobaCaptionBoldSecondaryDark() = MobaCaptionBold().copy(color = SecondaryDark)

@Composable
fun MobaFootnoteRegularSecondaryDark() = MobaFootnoteRegular().copy(color = SecondaryDark)

@Composable
fun BKSHeading4Small() = BKSHeading4().copy(fontSize = 16.sp, lineHeight = 19.2.sp)

@Composable
fun SwitchTextStyle() = TextStyle(
    fontFamily = getLatoFontFamily(),
    fontWeight = FontWeight.Bold,
    lineHeight = 13.2.sp,
    fontSize = 11.sp,
    textAlign = TextAlign.Center
)

val OpeningHoursTextStyle = TextStyle(
    fontFamily = FontFamily.Serif,
    color = SecondaryDarkest,
    fontSize = 16.sp
)

val ActionLabelTextStyle = TextStyle(
    fontWeight = FontWeight.Bold,
    color = TertiaryDarkest,
    fontSize = 9.sp
)
