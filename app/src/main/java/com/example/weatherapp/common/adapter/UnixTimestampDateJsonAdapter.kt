package com.example.weatherapp.common.adapter

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson
import java.io.IOException
import java.util.Date

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
