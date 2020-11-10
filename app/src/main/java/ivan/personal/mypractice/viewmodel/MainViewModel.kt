package ivan.personal.mypractice.viewmodel

import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import ivan.personal.mypractice.router.MainRouter
import ivan.personal.mypractice.view.MainAdapter

class MainViewModel @ViewModelInject constructor(private val router: MainRouter) : ViewModel() {

    // region Navigation

    /**
     * Navigate to material design graph
     */
    fun navigate(
        view: View,
        @MainAdapter.Companion.MainNavIndex position: Int
    ) {
        when (position) {
            MainAdapter.POSITION_MATERIAL_DESIGN -> router.toMaterialDesign(view = view)
            MainAdapter.POSITION_CAMERA -> router.toCamera(view = view)
        }
    }

    // endregion
}