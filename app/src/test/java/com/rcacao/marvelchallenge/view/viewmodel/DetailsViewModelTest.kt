package com.rcacao.marvelchallenge.view.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.rcacao.marvelchallenge.MockUtils
import com.rcacao.marvelchallenge.domain.model.DataResult
import com.rcacao.marvelchallenge.domain.model.comics.ComicsModel
import com.rcacao.marvelchallenge.domain.model.series.SeriesModel
import com.rcacao.marvelchallenge.domain.usecases.UseCase
import com.rcacao.marvelchallenge.view.model.details.comics.ComicsStateUi
import com.rcacao.marvelchallenge.view.model.details.series.SeriesStateUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.anyList


@ExperimentalCoroutinesApi
class DetailsViewModelTest {

    @Rule
    @JvmField
    var instantExecutorRule = InstantTaskExecutorRule()

    private val useCaseSeries: UseCase<String, DataResult<List<SeriesModel>>> = mock()
    private val useCaseComics: UseCase<String, DataResult<List<ComicsModel>>> = mock()

    private val observerComicsList: Observer<List<ComicsModel>> = mock()
    private val observerSeriesList: Observer<List<SeriesModel>> = mock()
    private val observerStatusComics: Observer<ComicsStateUi> = mock()
    private val observerStatusSeries: Observer<SeriesStateUi> = mock()
    private val textErrorHelper: TextErrorHelper = mock()

    private lateinit var viewModel: DetailsViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        whenever(textErrorHelper.invoke(any())).thenReturn("error")
        viewModel = DetailsViewModel(useCaseComics, useCaseSeries, textErrorHelper)
    }

    @Test
    fun getComicsSuccess() {
        runBlockingTest {
            viewModel.comicsList.observeForever(observerComicsList)
            viewModel.comicsStateUi.observeForever(observerStatusComics)

            val list = MockUtils.getComicsModelList()
            whenever(useCaseComics.invoke(any())).thenReturn(DataResult.Success(list))

            viewModel.getComics("")

            verify(observerStatusComics).onChanged(ComicsStateUi.Loading)
            verify(observerStatusComics).onChanged(ComicsStateUi.Loaded)
            verify(observerComicsList).onChanged(list)
        }
    }

    @Test
    fun getComicsEmpty() {
        runBlockingTest {
            viewModel.comicsList.observeForever(observerComicsList)
            viewModel.comicsStateUi.observeForever(observerStatusComics)

            val list = emptyList<ComicsModel>()
            whenever(useCaseComics.invoke(any())).thenReturn(DataResult.Success(list))

            viewModel.getComics("")

            verify(observerStatusComics).onChanged(ComicsStateUi.Loading)
            verify(observerStatusComics).onChanged(ComicsStateUi.Empty)
            verify(observerComicsList).onChanged(list)
        }
    }

    @Test
    fun getComicsError() {
        runBlockingTest {
            viewModel.comicsList.observeForever(observerComicsList)
            viewModel.comicsStateUi.observeForever(observerStatusComics)
            val list: List<ComicsModel> = MockUtils.getComicsModelList()
            whenever(useCaseComics.invoke(any())).thenReturn(DataResult.Error(Exception("error")))

            viewModel.getComics("")

            verify(observerComicsList).onChanged(anyList())
            verify(observerStatusComics).onChanged(ComicsStateUi.Error("error"))

        }
    }

    @Test
    fun getSeriesSuccess() {
        runBlockingTest {
            viewModel.seriesList.observeForever(observerSeriesList)
            viewModel.seriesStateUi.observeForever(observerStatusSeries)

            val list = MockUtils.getSeriesModelList()
            whenever(useCaseSeries.invoke(any())).thenReturn(DataResult.Success(list))

            viewModel.getSeries("")

            verify(observerStatusSeries).onChanged(SeriesStateUi.Loading)
            verify(observerStatusSeries).onChanged(SeriesStateUi.Loaded)
            verify(observerSeriesList).onChanged(list)
        }
    }

    @Test
    fun getSeriesEmpty() {
        runBlockingTest {
            viewModel.seriesList.observeForever(observerSeriesList)
            viewModel.seriesStateUi.observeForever(observerStatusSeries)

            val list = emptyList<SeriesModel>()
            whenever(useCaseSeries.invoke(any())).thenReturn(DataResult.Success(list))

            viewModel.getSeries("")

            verify(observerStatusSeries).onChanged(SeriesStateUi.Loading)
            verify(observerStatusSeries).onChanged(SeriesStateUi.Empty)
            verify(observerSeriesList).onChanged(list)
        }
    }

    @Test
    fun getSeriesError() {
        runBlockingTest {
            viewModel.seriesList.observeForever(observerSeriesList)
            viewModel.seriesStateUi.observeForever(observerStatusSeries)

            whenever(useCaseSeries.invoke(any())).thenReturn(DataResult.Error(Exception("erro")))

            viewModel.getSeries("")

            verify(observerStatusSeries).onChanged(SeriesStateUi.Loading)
            verify(observerStatusSeries).onChanged(SeriesStateUi.Error("error"))
            verify(observerSeriesList).onChanged(anyList())
        }
    }
}


