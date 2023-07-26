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
import wu.pei.cheng.taipeizoom.EventObserver
import wu.pei.cheng.taipeizoom.MainActivity
import wu.pei.cheng.taipeizoom.R
import wu.pei.cheng.taipeizoom.ViewModel
import wu.pei.cheng.taipeizoom.adapter.MainAdapter
import wu.pei.cheng.taipeizoom.databinding.FragmentMainBinding
import wu.pei.cheng.taipeizoom.model.AreaModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel by viewModels<ViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
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
            try {
                (activity as MainActivity).setToolBar("台北市立動物園", false)
            } catch (e: Exception) {

            }
        }
    }

    private fun initViews() {
        initToolBar()
        val recyclerView = _binding?.recyclerViewMain
        val adapter = MainAdapter(viewModel)

        recyclerView?.layoutManager = LinearLayoutManager(context)
        val itemAnimator = DefaultItemAnimator()
        itemAnimator.supportsChangeAnimations = false
        recyclerView?.itemAnimator = itemAnimator

        recyclerView?.adapter = adapter

        viewModel.getAreaData()
        viewModel.areaData.observe(viewLifecycleOwner, Observer {
            adapter.notifyDataSetChanged()
        })

        viewModel.areaDataClick.observe(viewLifecycleOwner, EventObserver{
            itemClick(it)
        })


    }

    fun itemClick(areaDataResult: AreaModel.Result.AreaDataResult?) {

        val bundle = bundleOf("data" to areaDataResult)
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
//        viewModel.areaDataClick.value = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}