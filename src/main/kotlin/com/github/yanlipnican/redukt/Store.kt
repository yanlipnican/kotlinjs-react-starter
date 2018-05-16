package com.github.yanlipnican.redukt

interface Action

typealias Dispatch = (action: Action) -> Unit
typealias ThunkAction<S, G> = (state: S, getters: G, dispatch: Dispatch) -> Unit
typealias Subscriber<S, G> = (state: S, getters: G) -> Unit
typealias Reducer<S, G> = (state: S, getters: G, action: Action) -> S

interface Store<S, G> {
    fun dispatch(action: Action)
    fun dispatch(thunkAction: ThunkAction<S, G>)
    fun subscribe(subscriber: Subscriber<S, G>)
    fun unsubscribe(subscriber: Subscriber<S, G>)
    val dispatchFunction: (action: Action) -> Unit
    val getters: G
    val state: S
}

class SingleStore<S, G>(
        private var innerState: S,
        private val getter: (state: S) -> G,
        private val reducer: Reducer<S, G>
    ): Store<S, G> {

    override val getters: G
        get() {
            return getter(innerState)
        }

    override val state: S
        get() {
            return innerState
        }

    private val subscribers = mutableListOf<Subscriber<S, G>>()

    override val dispatchFunction = { action: Action ->
        innerState = reducer(innerState, getters, action)
        notifySubscribers()
    }

    override fun dispatch(action: Action) = dispatchFunction(action)

    override fun dispatch(thunkAction: ThunkAction<S, G>) {
        thunkAction(innerState, getters, dispatchFunction)
    }

    private fun notifySubscribers() {
        subscribers.forEach {
            it(innerState, getters)
        }
    }

    override fun unsubscribe(subscriber: Subscriber<S, G>) {
        subscribers.remove(subscriber)
    }

    override fun subscribe(subscriber: Subscriber<S, G>) {
        subscribers.add(subscriber)
    }
}


/// testing

private data class AppState(val count: Int = 0)

private data class INCREMENT(val by: Int = 1) : Action
private data class DECREMENT(val by: Int = 1) : Action

private fun ASYNC_INCREMENT(number: Int): ThunkAction<AppState, Getters> {

    return { state, getters, dispatch ->

        dispatch(INCREMENT())

        if (state.count > number) {
            dispatch(DECREMENT())
        }
    }

}

private interface Getters {
    val count: Int
}


fun test() {

    val reducer = { state: AppState, getters: Getters, action: Action ->
        when(action) {
            is INCREMENT -> AppState(getters.count + action.by)
            is DECREMENT -> AppState(getters.count - action.by)
            else -> state
        }
    }

    val getters = { state: AppState ->
        object: Getters {

            override val count: Int get() {
                return state.count
            }

        }
    }

    val store = SingleStore(AppState(), getters, reducer)

    store.subscribe { state, getters ->
        console.log(getters.count)
    }

    store.dispatch(ASYNC_INCREMENT(4))

    store.dispatch(INCREMENT(10))
    store.dispatch(DECREMENT(4))

}