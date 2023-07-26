package wu.pei.cheng.taipeizoom.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import wu.pei.cheng.taipeizoom.MainActivity
import wu.pei.cheng.taipeizoom.R
import wu.pei.cheng.taipeizoom.ViewModel
import wu.pei.cheng.taipeizoom.databinding.FragmentAreaBinding
import wu.pei.cheng.taipeizoom.databinding.FragmentPlantBinding
import wu.pei.cheng.taipeizoom.model.AreaModel
import wu.pei.cheng.taipeizoom.model.PlantModel

class PlantFragment : Fragment() {

    private var _binding: FragmentPlantBinding? = null
    var param: PlantModel.Result.PlantDataResult? = null

    private val viewModel by viewModels<ViewModel>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        param = arguments?.getSerializable("data") as PlantModel.Result.PlantDataResult
        _binding = FragmentPlantBinding.inflate(inflater, container, false)
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
            param?.FNameCh?.let { (activity as MainActivity).setToolBar(it, true) }
        }
    }

    private fun initViews() {
        initToolBar()
        if(param is PlantModel.Result.PlantDataResult){
            val dataResult = param as PlantModel.Result.PlantDataResult

            binding?.imageOfPlant?.let {
                Glide.with(it.context)
                    .load(dataResult.fPic01URL)
                    .placeholder(com.google.android.material.R.drawable.mtrl_ic_error)
                    .centerCrop()
                    .into(it)
            }

            val stringBuffer = StringBuffer()
            stringBuffer.append(dataResult.FNameCh + "\n")
            stringBuffer.append(dataResult.fNameEn + "\n\n")
            stringBuffer.append(getString(R.string.also) + "\n")
            stringBuffer.append(dataResult.fAlsoKnown + "\n\n")
            stringBuffer.append(getString(R.string.intro) + "\n")
            stringBuffer.append(dataResult.fBrief + "\n\n")
            stringBuffer.append(getString(R.string.diff) + "\n")
            stringBuffer.append(dataResult.fFeature + "\n\n")
            stringBuffer.append(getString(R.string.func) + "\n")
            stringBuffer.append(dataResult.fFunction_Application + "\n\n")
            stringBuffer.append(getString(R.string.update) + dataResult.fUpdate)

            binding?.textOfPlant?.text = stringBuffer.toString()
        }
    }

}