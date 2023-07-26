package wu.pei.cheng.taipeizoom.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import wu.pei.cheng.taipeizoom.EventObserver
import wu.pei.cheng.taipeizoom.MainActivity
import wu.pei.cheng.taipeizoom.R
import wu.pei.cheng.taipeizoom.ViewModel
import wu.pei.cheng.taipeizoom.adapter.AreaAdapter
import wu.pei.cheng.taipeizoom.adapter.MainAdapter
import wu.pei.cheng.taipeizoom.databinding.FragmentAreaBinding
import wu.pei.cheng.taipeizoom.model.AreaModel
import wu.pei.cheng.taipeizoom.model.PlantModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AreaFragment : Fragment() {

    private var _binding: FragmentAreaBinding? = null
    var param: AreaModel.Result.AreaDataResult? = null

    private val viewModel by viewModels<ViewModel>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        param = arguments?.getSerializable("data") as AreaModel.Result.AreaDataResult
        _binding = FragmentAreaBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            initToolBar()
        }
    }

    private fun initToolBar() {
        if (activity is MainActivity) {
            param?.eName?.let { (activity as MainActivity).setToolBar(it, true) }
        }
    }

    private fun initViews() {
        initToolBar()
        Glide.with(this)
            .load(param?.ePicURL)
            .into(binding.topicImg)
        binding.tvInfo2.text = param?.eInfo
        binding.tvMemo2.text = param?.eMemo
        binding.tvCategory.text = param?.eCategory

        val recyclerView = binding.recyclerPlant
        val adapter = AreaAdapter(viewModel)

        recyclerView.layoutManager = LinearLayoutManager(context)
        val itemAnimator = DefaultItemAnimator()
        itemAnimator.supportsChangeAnimations = false
        recyclerView.itemAnimator = itemAnimator

        recyclerView.adapter = adapter

        param?.let { viewModel.getPlantData(it.eName) }
        viewModel.plantData.observe(viewLifecycleOwner, Observer {
            adapter.notifyDataSetChanged()
        })

        viewModel.plantDataClick.observe(viewLifecycleOwner, EventObserver {
            itemClick(it)
        })


    }

    fun itemClick(areaDataResult: PlantModel.Result.PlantDataResult?) {

        val bundle = bundleOf("data" to areaDataResult)
        findNavController().navigate(R.id.action_AreaFragment_to_PlantFragment, bundle)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}