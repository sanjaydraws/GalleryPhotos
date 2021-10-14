package com.sanjay.galleryphotos.extension

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import javax.inject.Inject

/**
 * Created By : Sanjay Prajapat
 * Time : 24/09/2021 on 12:23 AM
 *
 * */


/**
 * convert object to json
 * @param object type
 * @return String json
 * */
fun <T> Gson?.convertToJson( t:T ): String? =  this?.toJson(t)

/**
 * convert json to object
 * @param String json
 * @param T class type
 * @return String json
 * */
fun <T> Gson?.convertFromJson(json:String?, t:Class<T>):T? = this?.fromJson(json,t)


/*
* {"user1":{"address":{"firstAddress":"Green Street","secondAddress":"USA"},"age":23,"firstName":"Justin","lastName":" Bieber","numbers":["2345678","9039023"]},"i1":23}
* */
/**
 * convert map to json str
 * */
fun <T> Gson?.mapToJsonStr(map: Map<String?, T>?): String? =  this?.toJson(map)



/**
 * convert list of json string to Array List
 * */
inline fun <reified T> Gson?.convertToArrayList(listJson:String?): List<T>? {
    val tokenType: Type = object : TypeToken<ArrayList<T>>() {}.type
    return this?.fromJson(listJson, tokenType)
}
