package com.chatapp.frontend.lib

import com.chatapp.frontend.AppState
import com.chatapp.frontend.DECREMENT
import com.chatapp.frontend.INCREMENT
import com.github.yanlipnican.redukt.reduktConnect
import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.button
import react.dom.div

interface AppProps: RProps {
    var count: Int
    var onIncrement: () -> Unit
    var onDecrement: () -> Unit
}

class App: RComponent<AppProps, RState>() {

    override fun RBuilder.render() {
        div {

            +"count: ${props.count}"
            button {
                +"+"
                attrs.onClickFunction = {
                    props.onIncrement()
                }
            }
            button {
                +"-"
                attrs.onClickFunction = {
                    props.onDecrement()
                }
            }
        }
    }
}

fun RBuilder.App() = reduktConnect(
        App::class,
        { props, state: AppState, dispatch ->
            props.count = state.count
            props.onIncrement = {
                dispatch(INCREMENT)
            }
            props.onDecrement = {
                dispatch(DECREMENT)
            }
        }
)
