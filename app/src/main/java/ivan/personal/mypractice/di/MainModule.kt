package ivan.personal.mypractice.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import ivan.personal.mypractice.router.MainRouter
import ivan.personal.mypractice.view.MainAdapter

@Module
@InstallIn(ActivityRetainedComponent::class)
object MainModule {

    @Provides
    fun provideMainRouter(): MainRouter = MainRouter()

    @Provides
    fun provideMainAdapter(): MainAdapter = MainAdapter()
}