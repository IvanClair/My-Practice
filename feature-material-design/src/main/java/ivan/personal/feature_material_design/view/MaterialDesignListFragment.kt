package ivan.personal.feature_material_design.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import ivan.personal.feature_material_design.R
import ivan.personal.feature_material_design.databinding.FragmentMaterialDesignListBinding
import ivan.personal.feature_material_design.viewmodel.MaterialDesignViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MaterialDesignListFragment : Fragment() {

    // View binding
    private lateinit var binding: FragmentMaterialDesignListBinding

    // View model
    private val viewModel by navGraphViewModels<MaterialDesignViewModel>(R.id.nav_material_design) { defaultViewModelProviderFactory }

    // RecyclerView adapter
    @Inject
    lateinit var adapter: MaterialDesignListAdapter

    // region Life cycle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMaterialDesignListBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    // endregion

    // region Initial views

    private fun initRecyclerView() {
        binding.recyclerViewMaterial.apply {
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = this@MaterialDesignListFragment.adapter.apply {
                listener = { viewModel.navigate(view = binding.root, position = it) }
            }
        }
    }

    // endregion
}