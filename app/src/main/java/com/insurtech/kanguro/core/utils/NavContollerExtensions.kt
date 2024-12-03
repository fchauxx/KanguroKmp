package com.insurtech.kanguro.core.utils

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.*
import com.insurtech.kanguro.R

fun NavController.safeNavigate(
    @IdRes resId: Int,
    args: Bundle? = null,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    try {
        navigate(resId, args, navOptions, navigatorExtras)
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
    }
}

fun NavController.safeNavigate(directions: NavDirections, navOptions: NavOptions? = iOSLikeTransitions) {
    try {
        navigate(directions, navOptions)
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
    }
}

fun NavController.safeNavigate(directions: NavDirections, navigatorExtras: Navigator.Extras) {
    try {
        navigate(directions, navigatorExtras)
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
    }
}

val bottomSheetLikeTransitions = navOptions {
    anim {
        enter = R.anim.slide_in_bottom
        exit = R.anim.no_anim
        popEnter = R.anim.no_anim
        popExit = R.anim.slide_out_bottom
    }
}

val iOSLikeTransitions = navOptions {
    anim {
        enter = R.anim.enter_from_right
        exit = R.anim.exit_to_left
        popEnter = R.anim.enter_from_left
        popExit = R.anim.exit_to_right
    }
}
