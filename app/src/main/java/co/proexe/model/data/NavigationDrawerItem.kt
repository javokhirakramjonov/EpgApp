package co.proexe.model.data

import androidx.annotation.StringRes

data class NavigationDrawerItem(
    @StringRes
    val labelId: Int,
    val isSelected: Boolean
)