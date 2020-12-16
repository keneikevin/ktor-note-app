package com.kenei.routes

import com.kenei.data.checkPasswordForEmail
import com.kenei.data.request.AccountRequest
import com.kenei.data.resposes.SimpleResponse
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.loginRoute(){
    route("/login"){
        post {
            val request = try {
                call.receive<AccountRequest>()
            }catch (e: ContentTransformationException){
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            val isPasswordCorrect = checkPasswordForEmail(request.email,request.password)
            if (isPasswordCorrect){
                call.respond(OK, SimpleResponse(true,"You are now logged in"))}
            else{
                call.respond(OK,SimpleResponse(false, "The E-mail or password is incorrect"))
            }
        }
    }
}