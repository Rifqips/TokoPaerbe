package id.rifqipadisiliwangi.core.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.google.firebase.remoteconfig.ktx.remoteConfig
import id.rifqipadisiliwangi.core.data.local.database.ApplicationDatabase
import id.rifqipadisiliwangi.core.data.local.datastore.appDataSource
import id.rifqipadisiliwangi.core.data.local.datastore.datasource.AppPreferenceDataSource
import id.rifqipadisiliwangi.core.data.local.datastore.datasource.AppPreferenceDataSourceImpl
import id.rifqipadisiliwangi.core.data.network.api.datasource.StoreDataSourceImpl
import id.rifqipadisiliwangi.core.data.network.api.datasource.UserDataSource
import id.rifqipadisiliwangi.core.data.network.api.datasource.UserDataSourceImpl
import id.rifqipadisiliwangi.core.data.network.api.interceptor.AuthInterceptor
import id.rifqipadisiliwangi.core.data.network.api.interceptor.SessionInterceptor
import id.rifqipadisiliwangi.core.data.network.api.interceptor.TokenInterceptor
import id.rifqipadisiliwangi.core.data.network.api.service.ApplicationService
import id.rifqipadisiliwangi.core.domain.paging.StorePagingSource
import id.rifqipadisiliwangi.core.domain.repository.AppRoomRepository
import id.rifqipadisiliwangi.core.domain.repository.AppRoomRepositoryImpl
import id.rifqipadisiliwangi.core.domain.repository.AuthRepository
import id.rifqipadisiliwangi.core.domain.repository.AuthRepositoryImpl
import id.rifqipadisiliwangi.core.domain.repository.StoreRepository
import id.rifqipadisiliwangi.core.domain.repository.StoreRepositoryImpl
import id.rifqipadisiliwangi.core.domain.repository.firebase.FirebaseCrashlyticsLoggerImpl
import id.rifqipadisiliwangi.core.domain.repository.firebase.FirebaseDebugView
import id.rifqipadisiliwangi.core.domain.repository.firebase.FirebaseDebugViewImpl
import id.rifqipadisiliwangi.core.domain.repository.firebase.FirebaseLogger
import id.rifqipadisiliwangi.core.domain.repository.firebase.FirebaseRepository
import id.rifqipadisiliwangi.core.domain.repository.firebase.FirebaseRepositoryImpl
import id.rifqipadisiliwangi.core.domain.usecase.AppRoomUseCase
import id.rifqipadisiliwangi.core.domain.usecase.AuthInteractor
import id.rifqipadisiliwangi.core.domain.usecase.StoreInteractor
import id.rifqipadisiliwangi.core.utils.AssetWrapper
import id.rifqipadisiliwangi.core.utils.PreferenceDataStoreHelper
import id.rifqipadisiliwangi.core.utils.PreferenceDataStoreHelperImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModulesCore {

    private val firebaseModule = module{
        single { Firebase.analytics }
        single { Firebase.remoteConfig }
        single { Firebase.messaging }
        single <FirebaseLogger> { FirebaseCrashlyticsLoggerImpl() }
        single <FirebaseDebugView> { (FirebaseDebugViewImpl(get())) }
        single <FirebaseRepository> { (FirebaseRepositoryImpl(get())) }
    }

    private val localModule = module {
        single { androidContext().appDataSource }
        single { ApplicationDatabase.getInstance(get()) }
        single<PreferenceDataStoreHelper> { PreferenceDataStoreHelperImpl(get()) }
        single { get<ApplicationDatabase>().wishlistDao() }
        single { get<ApplicationDatabase>().notificationDao() }
        single { get<ApplicationDatabase>().cartDao() }
    }

    private val networkModule = module {
        single { ChuckerInterceptor(androidContext()) }
        single { AuthInterceptor(get()) }
        single { SessionInterceptor(get()) }
        single { TokenInterceptor(get(),get()) }
        single { ApplicationService.invoke(get(), get(), get(), get()) }
    }

    private val dataSourceModule = module {
        single<AppPreferenceDataSource> { AppPreferenceDataSourceImpl(get()) }
        single<UserDataSource> { UserDataSourceImpl(get()) }
        single{ StoreDataSourceImpl(get()) }
    }

    private val repositoryModule = module {
        single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
        single<StoreRepository> { StoreRepositoryImpl(get(), get()) }
        single<AppRoomRepository> { AppRoomRepositoryImpl(get(), get(), get()) }
    }

    private val useCaseModule = module {
        single { AuthInteractor(get(), get()) }
        single { StoreInteractor(get()) }
        factory { AppRoomUseCase(get()) }
    }
    private val utilsModule = module {
        single { AssetWrapper(androidContext()) }
    }

    private val pagingSource = module {
        single { StorePagingSource(get()) }
    }

    val modulesCore: List<Module> = listOf(
        localModule,
        networkModule,
        dataSourceModule,
        repositoryModule,
        utilsModule,
        useCaseModule,
        pagingSource,
        firebaseModule
    )
}
