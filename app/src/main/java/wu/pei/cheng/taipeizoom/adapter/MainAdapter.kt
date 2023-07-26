package wu.pei.cheng.taipeizoom.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import wu.pei.cheng.taipeizoom.ViewModel
import wu.pei.cheng.taipeizoom.BR
import wu.pei.cheng.taipeizoom.databinding.ItemMainBinding
import wu.pei.cheng.taipeizoom.model.AreaModel

class MainAdapter(private val viewModel: ViewModel) :
    RecyclerView.Adapter<MainAdapter.VH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun getItemCount(): Int = getItemList()?.size ?: 0

    override fun onBindViewHolder(holder: VH, position: Int) {
        val data = getItemList()?.get(position)
        holder.viewBinding.setVariable(BR.dataResult, data)
        holder.viewBinding.setVariable(BR.viewModel, viewModel)
        holder.viewBinding.executePendingBindings()
    }

    class VH(val viewBinding: ItemMainBinding) : RecyclerView.ViewHolder(viewBinding.root)

    fun getItemList(): List<AreaModel.Result.AreaDataResult>?{
        return viewModel.areaData.value?.result?.results
    }
}