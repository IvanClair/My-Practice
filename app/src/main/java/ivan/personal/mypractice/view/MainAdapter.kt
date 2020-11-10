package ivan.personal.mypractice.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.IntDef
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.scopes.FragmentScoped
import ivan.personal.core.setSafeOnClickListener
import ivan.personal.mypractice.databinding.VhMainBinding
import javax.inject.Inject

@FragmentScoped
class MainAdapter @Inject constructor() : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    // Data source
    private val dataSource: List<String> by lazy {
        listOf("Material Design", "Camera")
    }

    // Click listener
    var listener: ((Int) -> Unit)? = null

    // Constant
    companion object {
        @IntDef(
            POSITION_MATERIAL_DESIGN,
            POSITION_CAMERA
        )
        @Retention(AnnotationRetention.SOURCE)
        annotation class MainNavIndex

        const val POSITION_MATERIAL_DESIGN = 0
        const val POSITION_CAMERA = 1
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
        holder.apply {
            itemView setSafeOnClickListener { listener?.invoke(position) }
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