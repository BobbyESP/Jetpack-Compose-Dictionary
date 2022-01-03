package com.devaloteam.dictionary.feature_dictionary.data.repository

import com.devaloteam.dictionary.core.util.Resource
import com.devaloteam.dictionary.feature_dictionary.data.local.WordInfoDao
import com.devaloteam.dictionary.feature_dictionary.data.remote.DictionaryAPI
import com.devaloteam.dictionary.feature_dictionary.domain.model.WordInfo
import com.devaloteam.dictionary.feature_dictionary.domain.repository.WordInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class WordInfoRepositoryImpl(
    private val api: DictionaryAPI,
    private val dao: WordInfoDao
): WordInfoRepository {

    override fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>> = flow {
        emit(Resource.Loading())

        val wordInfos = dao.getWordInfos(word).map { it.toWordInfo() }
        emit(Resource.Loading(data = wordInfos))

        try {
            val remoteWordInfos = api.getWordInfo(word)
            dao.deleteWordInfos(remoteWordInfos.map { it.word })
            dao.insertWordInfos(remoteWordInfos.map { it.toWordInfoEntity() })

        }catch (e: HttpException){

            emit(Resource.Error(
                message = "Oh no... Algo fué mal!",
                data = wordInfos
            ))

        }catch (e: IOException){
            emit(Resource.Error(
                message = "No se pudo conectar al servidor. Porfavor, revisa tu conexión a internet.",
                data = wordInfos
            ))
        }

        val newWordInfos = dao.getWordInfos(word).map { it.toWordInfo() }
        emit(Resource.Success(newWordInfos))
    }
}