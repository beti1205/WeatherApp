package com.example.weatherapp.data.adapters

import com.squareup.moshi.*
import java.io.IOException
import java.util.*

class UnixTimestampDateJsonAdapter : JsonAdapter<Date>() {

    @FromJson
    @Synchronized
    @Throws(IOException::class)
    override fun fromJson(reader: JsonReader): Date {
        return Date(reader.nextLong() * 1000)
    }

    @ToJson
    @Synchronized
    @Throws(IOException::class)
    override fun toJson(writer: JsonWriter, value: Date?) {
        writer.value(value?.time?.div(1000))
    }

}