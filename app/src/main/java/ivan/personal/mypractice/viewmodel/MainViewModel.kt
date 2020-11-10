package ivan.personal.mypractice.viewmodel

import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import ivan.personal.mypractice.view.MainAdapter
import ivan.personal.mypractice.view.MainFragmentDirections

class MainViewModel @ViewModelInject constructor() : ViewModel() {

    // region Navigation

    /**
     * Navigate to material design graph
     */
    fun navigate(
        view: View,
        @MainAdapter.Companion.MainNavIndex position: Int
    ) {
        when (position) {
            MainAdapter.POSITION_MATERIAL_DESIGN -> navigateToMaterialDesign(view = view)
            MainAdapter.POSITION_CAMERA -> navigateToCamera(view = view)
        }
    }

    private fun navigateToMaterialDesign(view: View) {
        view.findNavController().navigate(MainFragmentDirections.toMaterialDesign())
    }

    private fun navigateToCamera(view: View) {
        view.findNavController().navigate(MainFragmentDirections.toCamera())
    }

    // endregion
}