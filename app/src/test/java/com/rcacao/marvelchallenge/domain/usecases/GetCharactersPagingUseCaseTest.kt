package com.rcacao.marvelchallenge.domain.usecases

import androidx.paging.PagingData
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.rcacao.marvelchallenge.data.repository.characters.CharactersRepository
import com.rcacao.marvelchallenge.domain.model.character.CharacterModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetCharactersPagingUseCaseTest {

    private var repository: CharactersRepository = mock()
    private lateinit var useCase: GetCharactersPagingUseCase

    @Before
    fun setup() {
        useCase = GetCharactersPagingUseCase(repository)
    }

    @Test
    fun testSuccess() {
        runBlockingTest {

            val flow: Flow<PagingData<CharacterModel>> = flow {}
            whenever(repository.getCharacters(any(), any())).thenReturn(flow)

            val requestData = GetCharactersPagingUseCase.CharacterPagingRequest("", true)
            val result: Flow<PagingData<CharacterModel>> = useCase.invoke(requestData)

            assertTrue(result == flow)
        }
    }


}