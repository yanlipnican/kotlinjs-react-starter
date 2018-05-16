package com.chatapp.frontend

import com.chatapp.frontend.lib.App
import com.github.yanlipnican.redukt.Action
import com.github.yanlipnican.redukt.ReduktProvider
import com.github.yanlipnican.redukt.SingleStore
import react.dom.render
import kotlin.browser.document

data class AppState(val count: Int = 0)

object INCREMENT: Action
object DECREMENT: Action

interface Getters

private val getters = { state: AppState ->
    object: Getters {

    }
}

private val reducer = { state: AppState, getters: Getters, action: Action ->
    when(action) {
        is INCREMENT -> AppState(state.count + 1)
        is DECREMENT -> AppState(state.count - 1)
        else -> state
    }
}

fun main(args: Array<String>) {

    val store = SingleStore(AppState(), getters, reducer)

    val rootDiv = document.getElementById("react-root")

    render(rootDiv) {
        App(store)
    }

}