package yokai.core.di

import uy.kohesive.injekt.api.InjektModule
import uy.kohesive.injekt.api.InjektRegistrar
import uy.kohesive.injekt.api.addFactory
import uy.kohesive.injekt.api.addSingletonFactory
import uy.kohesive.injekt.api.get
import yokai.data.chapter.ChapterRepositoryImpl
import yokai.data.extension.repo.ExtensionRepoRepositoryImpl
import yokai.data.library.custom.CustomMangaRepositoryImpl
import yokai.data.manga.MangaRepositoryImpl
import yokai.domain.chapter.ChapterRepository
import yokai.domain.chapter.interactor.GetAvailableScanlators
import yokai.domain.chapter.interactor.GetChapters
import yokai.domain.extension.interactor.TrustExtension
import yokai.domain.extension.repo.ExtensionRepoRepository
import yokai.domain.extension.repo.interactor.CreateExtensionRepo
import yokai.domain.extension.repo.interactor.DeleteExtensionRepo
import yokai.domain.extension.repo.interactor.GetExtensionRepo
import yokai.domain.extension.repo.interactor.GetExtensionRepoCount
import yokai.domain.extension.repo.interactor.ReplaceExtensionRepo
import yokai.domain.extension.repo.interactor.UpdateExtensionRepo
import yokai.domain.library.custom.CustomMangaRepository
import yokai.domain.library.custom.interactor.CreateCustomManga
import yokai.domain.library.custom.interactor.DeleteCustomManga
import yokai.domain.library.custom.interactor.GetCustomManga
import yokai.domain.library.custom.interactor.RelinkCustomManga
import yokai.domain.manga.MangaRepository
import yokai.domain.manga.interactor.GetLibraryManga

class DomainModule : InjektModule {
    override fun InjektRegistrar.registerInjectables() {
        addFactory { TrustExtension(get(), get()) }

        addSingletonFactory<ExtensionRepoRepository> { ExtensionRepoRepositoryImpl(get()) }
        addFactory { CreateExtensionRepo(get()) }
        addFactory { DeleteExtensionRepo(get()) }
        addFactory { GetExtensionRepo(get()) }
        addFactory { GetExtensionRepoCount(get()) }
        addFactory { ReplaceExtensionRepo(get()) }
        addFactory { UpdateExtensionRepo(get(), get()) }

        addSingletonFactory<CustomMangaRepository> { CustomMangaRepositoryImpl(get()) }
        addFactory { CreateCustomManga(get()) }
        addFactory { DeleteCustomManga(get()) }
        addFactory { GetCustomManga(get()) }
        addFactory { RelinkCustomManga(get()) }

        addSingletonFactory<MangaRepository> { MangaRepositoryImpl(get()) }
        addFactory { GetLibraryManga(get()) }

        addSingletonFactory<ChapterRepository> { ChapterRepositoryImpl(get()) }
        addFactory { GetAvailableScanlators(get()) }
        addFactory { GetChapters(get()) }
    }
}
