package com.devaloteam.dictionary.feature_dictionary.data.remote

import com.devaloteam.dictionary.feature_dictionary.data.remote.dto.WordInfoDto
import retrofit2.http.GET
import retrofit2.http.Path

interface  DictionaryAPI {

    //API URL to get the JSON Response with dictionary data. "en" string can be changed for the language that you want (see https://dictionaryapi.dev to know if the language you want is available)
    @GET("/api/v2/entries/en/{word}")
    suspend fun getWordInfo(
        @Path("word") word: String
    ): List<WordInfoDto>

    companion object {
        const val BASE_URL = "https://api.dictionaryapi.dev/"
    }
}