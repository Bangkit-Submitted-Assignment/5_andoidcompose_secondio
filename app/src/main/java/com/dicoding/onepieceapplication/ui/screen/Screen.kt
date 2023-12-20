package com.dicoding.onepieceapplication.ui.screen

sealed class Screen (val route:String){
    object main: Screen("main")
    object detail: Screen("detail/{id}"){
        fun createRoute(id:Long) = "detail/$id"
    }
    object profil: Screen("profil")

}