package com.example.base.base.util

import androidx.lifecycle.Observer
class SingleEventObserver <T>(private val onEventUnhandledContent: (T) -> Unit) :
    Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {
        event?.getContentIfNotHandled()?.let { value -> onEventUnhandledContent(value) }
    }
}