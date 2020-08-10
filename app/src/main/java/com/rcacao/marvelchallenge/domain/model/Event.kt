package com.rcacao.marvelchallenge.domain.model

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 * from https://gist.github.com/JoseAlcerreca/5b661f1800e1e654f07cc54fe87441af
 */
open class Event<out T>(private val content: T) {

    private var hasBeenHandled = false

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

}
