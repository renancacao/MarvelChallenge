package com.rcacao.marvelchallenge.view.viewmodel

import org.junit.Assert.assertTrue
import org.junit.Test

class TextErrorHelperTest {

    @Test
    fun testChangeText() {
        val textErrorHelper = TextErrorHelperImpl()
        assertTrue(textErrorHelper.invoke("error").contains("\uD83D\uDE28"))
    }
}