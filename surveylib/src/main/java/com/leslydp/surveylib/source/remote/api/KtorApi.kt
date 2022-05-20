package com.leslydp.surveylib.source.remote.api

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.Json


internal abstract class KtorApi{
    private val jsonConfiguration = Json{
        prettyPrint = true
        ignoreUnknownKeys = true
    }
    val client = HttpClient{
        install(JsonFeature){
            serializer = KotlinxSerializer(jsonConfiguration)
        }
        defaultRequest {
            header("Authorization", "Basic $trinidadApiKey")
        }

    }

    fun HttpRequestBuilder.apiUrl(path: String){
        url {
            takeFrom(TRINIDAD_API_ENDPOINT)
            path("api",path)
        }
    }
    companion object {
        private const val TRINIDAD_API_ENDPOINT = "https://trinidad-dashboard-server.herokuapp.com"
        private const val trinidadApiKey = "YXBwOjhhNzRiODA3YTA5NjlkYTBkM2Q3NGNjYzNhZQ"
    }
}