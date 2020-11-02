package ivan.personal.feature_material_design.viewmodel

import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import ivan.personal.feature_material_design.router.MaterialDesignRouter
import ivan.personal.feature_material_design.view.MaterialDesignListAdapter
import java.text.FieldPosition

class MaterialDesignViewModel @ViewModelInject constructor(private val router: MaterialDesignRouter) :
    ViewModel() {

    // region Navigation

    fun navigate(
        view: View,
        @MaterialDesignListAdapter.Companion.MaterialNavIndex position: Int
    ) {
        when (position) {
            MaterialDesignListAdapter.POSITION_BOTTOM_APP_BAR -> {
            }
        }
    }

    // endregion
}