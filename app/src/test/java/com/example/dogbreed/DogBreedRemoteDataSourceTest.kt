package com.example.dogbreed

import com.example.dogbreed.data.remote.DogBreedRemoteDataSource
import com.example.dogbreed.data.remote.DogBreedRemoteDataSourceImp
import com.example.dogbreed.data.remote.DogBreedService
import com.example.dogbreed.data.remote.model.AllBreedsResponse
import com.example.dogbreed.data.utils.Result
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DogBreedRemoteDataSourceTest {
    private lateinit var server: MockWebServer
    private lateinit var dataSource: DogBreedRemoteDataSource

    @Before
    fun setup() {
        server = MockWebServer()
        server.start()

        val retrofit = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        dataSource = DogBreedRemoteDataSourceImp(
            retrofit.create(DogBreedService::class.java)
        )
    }

    @After
    fun teardown() {
        server.shutdown()
    }

    @Test
    fun getAllDogBreed_Success() = runBlocking {
        // Set up a mocked response for the "/list/all" endpoint
        val response = MockResponse()
            .setResponseCode(200)
            .setBody(provideDogBreedResponse())
        server.enqueue(response)

        // Call the getAllDogBreeds method on the remote data source
        val dogBreeds = dataSource.getAllDogBreeds().toList()

        // Verify that the correct request was sent
        val request = server.takeRequest()
        assertEquals("GET", request.method)
        assertEquals("/list/all", request.path)

        // Verify that the correct response was received
        assertEquals(
            dogBreeds, listOf(
                Result.Loading,
                Result.Success(
                    AllBreedsResponse(
                        "success",
                        hashMapOf<String, List<String>>(
                            "affenpinscher" to listOf(),
                            "sharpei" to listOf(),
                            "australian" to listOf("shepherd"),
                            "sheepdog" to listOf("english", "shetland"),
                            "wolfhound" to listOf("irish"),
                        )
                    )
                )
            )
        )
    }

    @Test
    fun getAllDogBreed_Error() = runBlocking {
        // Set up a mocked response for the "/list/all" endpoint
        val response = MockResponse()
            .setResponseCode(404)
        server.enqueue(response)

        // Call the getAllDogBreeds method on the remote data source
        val dogBreeds = dataSource.getAllDogBreeds().toList()

        // Verify that the correct request was sent
        val request = server.takeRequest()
        assertEquals("GET", request.method)
        assertEquals("/list/all", request.path)

        // Verify that the correct response was received
        assertEquals(dogBreeds.size, 2)
        assertEquals(dogBreeds[0], Result.Loading)
        assert(dogBreeds[1] is Result.Error)
    }

    private fun provideDogBreedResponse() = """
        {
            "message": {
            "affenpinscher": [],
            "australian": [
            "shepherd"
            ],
            "sharpei": [],
            "sheepdog": [
                "english",
                "shetland"
            ],
            "wolfhound": [
                "irish"
            ]
            },
            "status": "success" 
        }
        """
}