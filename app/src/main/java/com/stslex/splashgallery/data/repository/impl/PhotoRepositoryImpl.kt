package com.stslex.splashgallery.data.repository.impl

import com.stslex.splashgallery.data.data_source.interf.PhotoSource
import com.stslex.splashgallery.data.model.domain.image.ImageModel
import com.stslex.splashgallery.data.repository.interf.PhotoRepository
import com.stslex.splashgallery.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(private val photoSource: PhotoSource) :
    PhotoRepository {
    override suspend fun getAllPhotos(pageNumber: Int): Flow<Result<List<ImageModel>>> =
        photoSource.getAllPhotos(pageNumber)

    override suspend fun getCurrentPhoto(id: String): Result<ImageModel> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                when (val response = photoSource.getCurrentPhoto(id)) {
                    is Result.Success -> {
                        Result.Success(response.data)
                    }
                    is Result.Failure -> {
                        Result.Failure(response.exception)
                    }
                    else -> {
                        Result.Loading
                    }
                }
            } catch (exception: Exception) {
                Result.Failure(exception.toString())
            }
        }
}