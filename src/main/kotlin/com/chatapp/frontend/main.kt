package com.chatapp.frontend

import com.brianegan.bansa.*

data class State(val count: Int = 0)

class MainReducer() : Reducer<State> {

    object INCREMENT: Action
    object DECREMENT: Action

    override fun reduce(state: State, action: Action): State {
        return when (action) {
            INCREMENT -> state.copy(count = state.count + 1)0
            DECREMENT -> state.copy(count = state.count - 1)
            else -> state
        }
    }
}

fun main(args: Array<String>) {

    val counterStore = BaseStore(State(), MainReducer());

}