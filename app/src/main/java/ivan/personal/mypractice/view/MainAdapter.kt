package ivan.personal.mypractice.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ivan.personal.mypractice.databinding.VhMainBinding

class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    // data source
    private val dataSource: List<String> by lazy {
        listOf("Material")
    }

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
        dataSource
            .getOrNull(position)
            ?.run { holder.bind(subjectName = this) }
    }

    override fun getItemCount(): Int = dataSource.size

    // endregion

    // region View holder

    class MainViewHolder(private val binding: VhMainBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Bind data to [VhMainBinding]
         */
        fun bind(subjectName: String) {
            binding.textViewSubjectName.text = subjectName
        }
    }

    // endregion
}