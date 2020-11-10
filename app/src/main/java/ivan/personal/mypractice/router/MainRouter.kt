package ivan.personal.mypractice.router

import android.view.View
import androidx.navigation.findNavController
import ivan.personal.mypractice.view.MainFragmentDirections
import javax.inject.Inject

class MainRouter @Inject constructor() {

    /**
     * Navigate to material design graph
     */
    fun toMaterialDesign(view: View) {
        view.findNavController().navigate(MainFragmentDirections.toMaterialDesign())
    }

    /**
     * Navigate to camera graph
     */
    fun toCamera(view: View) {
        view.findNavController().navigate(MainFragmentDirections.toCamera())
    }
}