package com.example.final_android_lvl1.presentetion.basic.search

sealed class State {
    object Loading : State()
    object Success : State()
    object Error : State()
}