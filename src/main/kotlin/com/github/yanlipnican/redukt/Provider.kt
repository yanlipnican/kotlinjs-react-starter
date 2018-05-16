package com.github.yanlipnican.redukt

import kotlinext.js.asJsObject
import react.*

interface ReduktProviderProps: RProps {
    var store: Store<*, *>
}

interface ReduktProviderContext: RContext {
    val store: Store<*, *>
}

class ReduktProvider: RComponent<ReduktProviderProps, RState>() {

    companion object {
        public val childContextTypes = object {
            public val store = PropTypes.any
        }
    }

    override fun getChildContext(): RContext? {
        return object: ReduktProviderContext {
            override val store = props.store
        }
    }

    override fun RBuilder.render() {
        children()
    }

}

fun <S, G> RBuilder.ReduktProvider(store: Store<S, G>, handler: RHandler<ReduktProviderProps>) = child(ReduktProvider::class) {
    attrs.store = store
    console.log("provider class", this.asJsObject())
    handler()
}