package com.github.yanlipnican.redukt

import kotlinext.js.asJsObject
import react.*
import kotlin.reflect.KClass

typealias MapStateToProps<S, P> = (props: P, state: S, dispatch: Dispatch) -> Unit

private interface ConnectProps<S, P : RProps, C: RComponent<P, *>>: RProps {
    var mapStateToProps: MapStateToProps<S, P>
    var component: KClass<C>
}

private class Connect<S, P: RProps, C: RComponent<P, *>>: RComponent<ConnectProps<S, P, C>, RState>() {

    companion object {
        public val contextTypes = object {
            public val store = PropTypes.any
        }
    }

    private val store: Store<S, *> get() {
        val ctx = context as ReduktProviderContext
        return ctx.store as Store<S, *>
    }

    override fun componentDidMount() {

        // TODO: unsubscribe
        store.subscribe { state, _ ->
            forceUpdate()
        }
    }

    override fun RBuilder.render() {
        child(props.component) {
            props.mapStateToProps(attrs, store.state, store.dispatchFunction)
        }
    }

}


fun <S, P: RProps, C : RComponent<P, *>>RBuilder.reduktConnect(component: KClass<C>, mapStateToProps: MapStateToProps<S, P>) = child(Connect::class as KClass<Connect<S, P, C>>) {
    attrs.component = component
    attrs.mapStateToProps = mapStateToProps
}