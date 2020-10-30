package ivan.personal.mypractice.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.scopes.FragmentScoped
import ivan.personal.mypractice.databinding.VhMainBinding
import javax.inject.Inject

@FragmentScoped
class MainAdapter @Inject constructor() : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    // Data source
    private val dataSource: List<String> by lazy {
        listOf("Material")
    }

    // Click listener
    var listener: ((Int) -> Unit)? = null

    // region Override

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainViewHolder = MainViewHolder(
        binding = VhMainBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(
        holder: MainViewHolder,
        position: Int
    ) {
        holder.apply {
            itemView.setOnClickListener { listener?.invoke(position) }
            bind(subjectName = dataSource.getOrNull(position))
        }
    }

    override fun getItemCount(): Int = dataSource.size

    // endregion

    // region View holder

    inner class MainViewHolder(private val binding: VhMainBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Bind data to [VhMainBinding]
         */
        fun bind(subjectName: String?) {
            binding.textViewSubjectName.text = subjectName
        }
    }

    // endregion
}