package com.insurtech.kanguro.designsystem.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.insurtech.kanguro.designsystem.R

val BKLHeading3 = TextStyle(
    fontFamily = FontFamily(Font(R.font.museo_sans)),
    fontWeight = FontWeight.SemiBold,
    color = SecondaryDarkest,
    lineHeight = 37.5.sp,
    fontSize = 31.25.sp
)

val MSansSemiBoldSecondaryDarkest21 = TextStyle(
    fontFamily = FontFamily(Font(R.font.museo_sans)),
    fontWeight = FontWeight.SemiBold,
    color = SecondaryDarkest,
    fontSize = 21.sp,
    lineHeight = 25.2.sp
)

val LatoBoldSecondaryDarkest16 = TextStyle(
    fontFamily = FontFamily(Font(R.font.lato)),
    fontWeight = FontWeight.Bold,
    color = SecondaryDarkest,
    fontSize = 16.sp
)

val MobaBodyRegular = TextStyle(
    fontFamily = FontFamily(Font(R.font.lato)),
    fontWeight = FontWeight.W400,
    color = SecondaryDarkest,
    lineHeight = 19.2.sp,
    fontSize = 16.sp
)

val MobaCaptionRegular = TextStyle(
    fontFamily = FontFamily(Font(R.font.lato)),
    fontWeight = FontWeight.W400,
    color = SecondaryDarkest,
    lineHeight = 12.sp,
    fontSize = 12.sp
)

val MobaTitle1 = TextStyle(
    fontFamily = FontFamily(Font(R.font.museo_sans)),
    fontWeight = FontWeight.SemiBold,
    color = SecondaryDarkest,
    lineHeight = 38.4.sp,
    fontSize = 32.sp
)

val MobaTitle3 = TextStyle(
    fontFamily = FontFamily(Font(R.font.museo_sans)),
    fontWeight = FontWeight.Normal,
    color = SecondaryDarkest,
    lineHeight = 28.8.sp,
    fontSize = 24.sp
)

val MobaTitle3Light = MobaTitle3.copy(fontWeight = FontWeight.Light)

val MobaTitle3SemiBold = MobaTitle3.copy(fontWeight = FontWeight.SemiBold)

val Heading6ExtraBold = TextStyle(
    fontFamily = FontFamily(Font(R.font.lato)),
    fontWeight = FontWeight.ExtraBold,
    color = SecondaryDarkest,
    lineHeight = 19.2.sp,
    fontSize = 16.sp
)

val MobaFootnoteRegular = TextStyle(
    fontFamily = FontFamily(Font(R.font.lato)),
    fontWeight = FontWeight.W400,
    color = SecondaryLight,
    lineHeight = 11.sp,
    fontSize = 11.sp
)

val MobaSubheadRegular = TextStyle(
    fontFamily = FontFamily(Font(R.font.lato)),
    fontWeight = FontWeight.W400,
    color = SecondaryDarkest,
    lineHeight = 16.8.sp,
    fontSize = 14.sp
)

val MobaSubheadBlack = MobaSubheadRegular.copy(fontWeight = FontWeight.Black)

val MobaHeadline = TextStyle(
    fontFamily = FontFamily(Font(R.font.museo_sans)),
    fontWeight = FontWeight.SemiBold,
    color = SecondaryDarkest,
    lineHeight = 25.2.sp,
    fontSize = 21.sp
)

val BKSParagraphRegular = TextStyle(
    fontFamily = FontFamily(Font(R.font.lato)),
    fontWeight = FontWeight.W400,
    color = SecondaryMedium,
    lineHeight = 15.6.sp,
    fontSize = 13.sp
)

val ClickableTextStyle = TextStyle(
    fontFamily = FontFamily(Font(R.font.lato)),
    fontWeight = FontWeight.Bold,
    fontStyle = FontStyle.Italic,
    color = TertiaryDarkest,
    lineHeight = 15.6.sp,
    fontSize = 13.sp,
    textDecoration = TextDecoration.Underline
)

val ClickableTextStyleNormal = TextStyle(
    fontFamily = FontFamily(Font(R.font.lato)),
    fontWeight = FontWeight.Bold,
    color = TertiaryExtraDark,
    lineHeight = 11.sp,
    fontSize = 11.sp,
    textDecoration = TextDecoration.Underline
)

val MSansSemiBoldSecondaryDarkest25 = TextStyle(
    fontFamily = FontFamily(Font(R.font.museo_sans)),
    fontWeight = FontWeight.SemiBold,
    color = SecondaryDarkest,
    fontSize = 25.sp
)

val LatoBoldNeutralMedium2Size10 = TextStyle(
    fontSize = 10.sp,
    fontFamily = FontFamily(Font(R.font.lato)),
    fontWeight = FontWeight.Bold,
    color = NeutralMedium2
)

val SmallRegular = TextStyle(
    fontSize = 12.8.sp,
    lineHeight = 15.36.sp,
    fontFamily = FontFamily(Font(R.font.lato)),
    fontWeight = FontWeight.Normal,
    color = NavyBlue
)

val MobaSubheadLight = TextStyle(
    fontFamily = FontFamily(Font(R.font.museo_sans)),
    fontWeight = FontWeight.Light,
    color = SecondaryDark,
    lineHeight = 16.8.sp,
    fontSize = 14.sp
)

val LatoRegularSecondaryDarkSize12 = TextStyle(
    fontSize = 12.sp,
    lineHeight = 14.4.sp,
    fontFamily = FontFamily(Font(R.font.lato)),
    fontWeight = FontWeight.W400,
    color = SecondaryDark
)

val TextButtonStyle = TextStyle(
    fontFamily = FontFamily(Font(R.font.lato)),
    fontWeight = FontWeight.ExtraBold,
    lineHeight = 19.2.sp,
    fontSize = 16.sp
)

val BKSHeading4 = TextStyle(
    fontFamily = FontFamily(Font(R.font.museo_sans)),
    fontWeight = FontWeight.SemiBold,
    color = SecondaryDark,
    lineHeight = 24.37.sp,
    fontSize = 20.31.sp,
    textAlign = TextAlign.Center
)

val LatoBoldNeutral = TextStyle(
    fontSize = 10.4.sp,
    lineHeight = 12.48.sp,
    fontFamily = FontFamily(Font(R.font.lato)),
    fontWeight = FontWeight.Bold,
    color = NeutralMedium2
)

val MuseoSans15RegularSecondaryDark = TextStyle(
    fontSize = 15.sp,
    lineHeight = 18.sp,
    fontFamily = FontFamily(Font(R.font.museo_sans)),
    fontWeight = FontWeight.Normal,
    color = SecondaryDark,
    textAlign = TextAlign.Center
)

val MobaBodyBold = MobaBodyRegular.copy(fontWeight = FontWeight.Bold)

val MobaBodyItalic = MobaBodyRegular.copy(fontStyle = FontStyle.Italic)

val MobaCaptionBold = MobaCaptionRegular.copy(fontWeight = FontWeight.Bold)

val MobaFootnoteBlack = MobaFootnoteRegular.copy(fontWeight = FontWeight.Black)

val BKSParagraphBlack =
    BKSParagraphRegular.copy(fontWeight = FontWeight.Black, color = SecondaryDarkest)

val BKSParagraphBold =
    BKSParagraphRegular.copy(fontWeight = FontWeight.Bold, color = SecondaryDarkest)

val BKSParagraphRegularSDarkest = BKSParagraphRegular.copy(color = SecondaryDarkest)

val MobaCaptionRegularSDark = MobaCaptionRegular.copy(color = SecondaryDark)

val MobaBodyRegularSDark = MobaBodyRegular.copy(color = SecondaryDark)

val MobaBodyRegularTD = MobaBodyRegular.copy(color = TertiaryDarkest)

val MobaSubheadBold = MobaSubheadRegular.copy(fontWeight = FontWeight.Bold)

val MobaSubheadBoldSecondaryMedium = MobaSubheadBold.copy(color = SecondaryMedium)

val MobaCaptionRegularNeutralLightest = MobaCaptionRegular.copy(color = NeutralLightest)

val MobaBodyRegularNeutralLightest = MobaBodyRegular.copy(color = NeutralLightest)

val MobaSubheadRegularNegativeMedium = MobaSubheadRegular.copy(color = NegativeMedium)

val MobaTitle1PrimaryDarkest = MobaTitle1.copy(color = PrimaryDarkest)

val MobaSubheadRegularSecondaryDark = MobaSubheadRegular.copy(color = SecondaryDark)

val SmallRegularBold = SmallRegular.copy(fontWeight = FontWeight.Bold, color = SecondaryDark)

val MobaSubheadRegularSecondaryMedium = MobaSubheadRegular.copy(color = SecondaryMedium)

val MobaCaptionBoldSecondaryDark = MobaCaptionBold.copy(color = SecondaryDark)

val MobaFootnoteRegularSecondaryDark = MobaFootnoteRegular.copy(color = SecondaryDark)

val BKSHeading4Small = BKSHeading4.copy(fontSize = 16.sp, lineHeight = 19.2.sp)

val SwitchTextStyle = TextStyle(
    fontFamily = FontFamily(Font(R.font.lato)),
    fontWeight = FontWeight.Bold,
    lineHeight = 13.2.sp,
    fontSize = 11.sp,
    textAlign = TextAlign.Center
)

val OpeningHoursTextStyle = TextStyle(
    fontFamily = FontFamily(Font(R.font.lato)),
    color = SecondaryDarkest,
    fontSize = 16.sp
)

val ActionLabelTextStyle = TextStyle(
    fontWeight = FontWeight.Bold,
    color = TertiaryDarkest,
    fontSize = 9.sp
)
