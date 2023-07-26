package wu.pei.cheng.taipeizoom

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import wu.pei.cheng.taipeizoom.model.AreaModel
import wu.pei.cheng.taipeizoom.model.PlantModel
import wu.pei.cheng.taipeizoom.retrofit.RetrofitManager

class ViewModel : ViewModel() {
    val areaData : MutableLiveData<AreaModel?> = MutableLiveData()
    val plantData : MutableLiveData<PlantModel?> = MutableLiveData()

    val areaDataClick : MutableLiveData<Event<AreaModel.Result.AreaDataResult?>> = MutableLiveData()
    val plantDataClick : MutableLiveData<Event<PlantModel.Result.PlantDataResult?>> = MutableLiveData()

    // TODO: Implement the ViewModel

    fun getAreaData(): Job {
        return viewModelScope.launch {
            areaData.value =  RetrofitManager.getAreaData()
            Log.e("ViewModel",areaData.value.toString())
        }
    }

    fun getPlantData(plant: String): Job {
        return viewModelScope.launch {
            plantData.value =  RetrofitManager.getPlantData(plant)
            Log.e("ViewModelP",plantData.value.toString())
        }
    }

    fun areaDataClick(dataResult: AreaModel.Result.AreaDataResult) {
        areaDataClick.value = Event(dataResult)
    }

    fun plantDataClick(dataResult: PlantModel.Result.PlantDataResult) {
        plantDataClick.value = Event(dataResult)
    }
}


class Event<out T>(val content: T) {

    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): T? {
        if (hasBeenHandled) {
            return null
        } else {
            hasBeenHandled = true
            return content
        }
    }
}

class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>) {
        event.getContentIfNotHandled()?.let { value ->
            onEventUnhandledContent(value)
        }
    }

}