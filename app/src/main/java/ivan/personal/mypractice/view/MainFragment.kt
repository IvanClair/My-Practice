package ivan.personal.mypractice.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import ivan.personal.mypractice.R
import ivan.personal.mypractice.databinding.FragmentMainBinding
import ivan.personal.mypractice.viewmodel.MainViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    // View binding
    private lateinit var binding: FragmentMainBinding

    // View model
    private val viewModel by navGraphViewModels<MainViewModel>(R.id.nav_main)

    // Adapter
    @Inject
    lateinit var adapter: MainAdapter

    // region Life cycle

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
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

    // region Recycler view

    private fun initRecyclerView() {
        binding.recyclerViewMain.apply {
            val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            addItemDecoration(divider)
            adapter = this@MainFragment.adapter
            this@MainFragment.adapter.listener = {
            }
        }
    }

    // endregion
}