package wu.pei.cheng.taipeizoom.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import wu.pei.cheng.taipeizoom.model.AreaModel
import wu.pei.cheng.taipeizoom.model.PlantModel

class RetrofitManager {
    companion object {
        private const val baseUrl = "https://data.taipei/"
        const val SCOPE = "resourceAquire"
        private var apiService: ApiService? = null

        fun getApiService(): ApiService? {
            if(apiService == null){
                apiService = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiService::class.java)
            }
            return apiService
        }

        suspend fun getAreaData(): AreaModel? {
            return getApiService()?.areaData(SCOPE)
        }

        suspend fun getPlantData(plant : String): PlantModel? {
            return getApiService()?.plantData(SCOPE, plant)
        }
    }
}