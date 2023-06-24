package com.example.apimoviedbappsbagus.Service

import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class DefaultFormat {

    fun initdate(dateTime:String) :String
    {
        var odt = OffsetDateTime.parse(dateTime);
        var dtf = DateTimeFormatter.ofPattern("MMM dd, uuuu", Locale.ENGLISH);
        return (dtf.format(odt));

    }
}