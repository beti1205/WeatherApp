package com.example.weatherapp.feature.fetchplacebycoorinates.domain

import com.example.weatherapp.common.AppConfig
import com.example.weatherapp.common.LocaleProvider
import com.example.weatherapp.common.Result
import com.example.weatherapp.data.remotesource.FakeReverseGeocodingService
import com.example.weatherapp.feature.fetchplacebycoorinates.data.Place
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.util.*

@OptIn(ExperimentalCoroutinesApi::class)
class FetchPlaceByCoordinatesUseCaseTest {

    private val place = Place(mapOf("en" to "Warsaw"), 52.2319581, 21.0067249)
    private val places = listOf(place)
    private val appConfig = AppConfig(
        "weatherbaseUrl",
        "reverseGeocodingBaseUrl",
        "geocodingUrl",
        "apiKey",
        1,
        "units"
    )

    private lateinit var fakeReverseGeocodingService: FakeReverseGeocodingService
    private lateinit var fakeLocaleProvider: FakeLocaleProvider

    // class under test
    private lateinit var fetchPlaceByCoordinatesUseCase: FetchPlaceByCoordinatesUseCase

    @Before
    fun createUseCase() {
        fakeReverseGeocodingService = FakeReverseGeocodingService(places)
        fakeLocaleProvider = FakeLocaleProvider()
        fetchPlaceByCoordinatesUseCase = FetchPlaceByCoordinatesUseCaseImpl(
            fakeLocaleProvider,
            fakeReverseGeocodingService,
            appConfig
        )
    }

    @Test
    fun verifyThatPlaceWasFetchedCorrectly() = runBlockingTest {
        val result = fetchPlaceByCoordinatesUseCase(
            52.2319581,
            21.0067249
        ) as Result.Success

        assertEquals(place.localNames.values.first(), result.data)
    }

    @Test
    fun verifyThatNullIsReturnedWhenLocalNamesOnlyUnknownLocales() = runBlockingTest {
        fakeReverseGeocodingService.places = listOf(place.copy(localNames = mapOf("xyz" to "zyx")))

        val result = fetchPlaceByCoordinatesUseCase(
            52.2319581,
            21.0067249
        ) as Result.Success

        assertNull(result.data)
    }

    @Test
    fun verifyThatErrorIsReturnedWhenWebserviceReturnsError() = runBlockingTest {
        fakeReverseGeocodingService.isSuccessful = false

        val result = fetchPlaceByCoordinatesUseCase(
            52.2319581,
            21.0067249
        )

        assertTrue(result is Result.Error)
    }
}

private class FakeLocaleProvider : LocaleProvider {
    override fun getLocale(): Locale {
        return Locale.ENGLISH
    }
}
