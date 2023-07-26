package wu.pei.cheng.taipeizoom.retrofit

import retrofit2.http.GET
import retrofit2.http.Query
import wu.pei.cheng.taipeizoom.model.AreaModel
import wu.pei.cheng.taipeizoom.model.PlantModel

interface ApiService {

    @GET("api/v1/dataset/5a0e5fbb-72f8-41c6-908e-2fb25eff9b8a")
    suspend fun areaData(
        @Query("scope") scope: String
    ): AreaModel

    @GET("api/v1/dataset/f18de02f-b6c9-47c0-8cda-50efad621c14")
    suspend fun plantData(
        @Query("scope") scope: String,
        @Query("q") q: String
    ): PlantModel

}