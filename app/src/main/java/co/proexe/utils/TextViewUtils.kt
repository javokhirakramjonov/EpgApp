package co.proexe.utils

import android.content.Context
import androidx.appcompat.widget.AppCompatTextView
import co.proexe.R

fun AppCompatTextView.changeColorBySelection(context: Context, isSelected: Boolean) {
    if (isSelected) {
        setTextColor(resources.getColor(R.color.colorAccent, context.theme))
    } else {
        setTextColor(
            resources.getColor(
                R.color.colorRecyclerViewTimeItemNotSelected,
                context.theme
            )
        )
    }
}