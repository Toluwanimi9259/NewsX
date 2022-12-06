package com.themafia.apps.newsx.retrofit.api

import com.google.common.truth.Truth.assertThat
import com.themafia.apps.newsx.data.retrofit.api.NewsService
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsServiceTest {
    private lateinit var newsService : NewsService
    private lateinit var server : MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        newsService = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsService::class.java)
    }

    private fun enqueueMockResponse(
        fileName:String
    ){
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)

    }

    @Test
    fun getTopHeadlines_sentRequest_receivedExpected(){
        runBlocking {
            enqueueMockResponse("newsapiresponse.json")
            val responseBody = newsService.getTopHeadlines("us",1).body()
            val request = server.takeRequest()
            assertThat(responseBody).isNotNull()
            assertThat(request.path).isEqualTo("/v2/top-headlines?country=us&page=1&apiKey=91f6a48694c8469789c4bdd181c480ff")
        }
    }

    @Test
    fun getTopHeadlines_receivedResponse_correctPageSize(){
        runBlocking {
            enqueueMockResponse("newsapiresponse.json")
            val responseBody = newsService.getTopHeadlines("us",1).body()
            val articlesList = responseBody!!.articles
            assertThat(articlesList.size).isEqualTo(20)

        }
    }

    @Test
    fun getTopHeadlines_receivedResponse_correctContent(){
        runBlocking {
            enqueueMockResponse("newsapiresponse.json")
            val responseBody = newsService.getTopHeadlines("us",1).body()
            val articlesList = responseBody!!.articles
            val article = articlesList[0]
            assertThat(article.author).isEqualTo("Bill Hutchinson, Aaron Katersky, Matt Foster")
            assertThat(article.url).isEqualTo("https://abcnews.go.com/US/intentional-vandalism-suspected-north-carolina-county-power-outage/story?id=94438516")
            assertThat(article.publishedAt).isEqualTo("2022-12-05T18:11:15Z")
        }
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}