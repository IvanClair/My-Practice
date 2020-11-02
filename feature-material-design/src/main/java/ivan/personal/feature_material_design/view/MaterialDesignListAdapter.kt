package ivan.personal.feature_material_design.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.IntDef
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.scopes.FragmentScoped
import ivan.personal.core.setSafeOnClickListener
import ivan.personal.feature_material_design.databinding.VhMaterialDesignListBinding
import javax.inject.Inject

@FragmentScoped
class MaterialDesignListAdapter @Inject constructor() :
    RecyclerView.Adapter<MaterialDesignListAdapter.MaterialDesignListViewHolder>() {

    // Data source
    private val dataSource: List<String> by lazy {
        listOf("Bottom app bars")
    }

    // Click Listener
    var listener: ((Int) -> Unit)? = null

    // Constant
    companion object {
        @IntDef(
            POSITION_BOTTOM_APP_BAR
        )
        @Retention(AnnotationRetention.SOURCE)
        annotation class MaterialNavIndex

        const val POSITION_BOTTOM_APP_BAR = 0
    }

    // region Override

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MaterialDesignListViewHolder = MaterialDesignListViewHolder(
        binding = VhMaterialDesignListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(
        holder: MaterialDesignListViewHolder,
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

    inner class MaterialDesignListViewHolder(private val binding: VhMaterialDesignListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(subjectName: String?) {
            binding.textViewSubjectName.text = subjectName
        }
    }

    // endregion
}