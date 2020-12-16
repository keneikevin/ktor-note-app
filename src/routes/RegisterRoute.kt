package com.kenei.routes

import com.kenei.data.checkIfUserExists
import com.kenei.data.collections.User
import com.kenei.data.registerUser
import com.kenei.data.request.AccountRequest
import com.kenei.data.resposes.SimpleResponse
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.features.ContentTransformationException
import io.ktor.http.*
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.registerRoute(){
    route("/register"){
        post {
        val request = try {
        call.receive<AccountRequest>()
        } catch (e:ContentTransformationException){
            call.respond(BadRequest)
            return@post
        }
            val userExists = checkIfUserExists(request.email)
            if (!userExists){
                if (registerUser(User(request.email,request.password))){
                    call.respond(OK, SimpleResponse(true,"Successfully created account!"))
                } else{
                    call.respond(OK, SimpleResponse(false,"an unknown error occurred"))
                }
            } else{
                call.respond(OK, SimpleResponse(false,"A user with that E-mail already exists"))
            }
        }
    }
}