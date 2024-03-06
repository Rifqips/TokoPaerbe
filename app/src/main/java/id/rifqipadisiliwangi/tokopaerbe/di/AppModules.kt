package id.rifqipadisiliwangi.tokopaerbe.di

import id.rifqipadisiliwangi.tokopaerbe.presentation.feature.cart.CartViewModel
import id.rifqipadisiliwangi.tokopaerbe.presentation.feature.checkout.CheckoutViewModel
import id.rifqipadisiliwangi.tokopaerbe.presentation.feature.dashboard.DashboardViewModel
import id.rifqipadisiliwangi.tokopaerbe.presentation.feature.detail.DetailViewModel
import id.rifqipadisiliwangi.tokopaerbe.presentation.feature.home.HomeViewModel
import id.rifqipadisiliwangi.tokopaerbe.presentation.feature.login.LoginViewModel
import id.rifqipadisiliwangi.tokopaerbe.presentation.feature.notification.NotificationViewModel
import id.rifqipadisiliwangi.tokopaerbe.presentation.feature.onboarding.OnboardingViewModel
import id.rifqipadisiliwangi.tokopaerbe.presentation.feature.payment.PaymentViewModel
import id.rifqipadisiliwangi.tokopaerbe.presentation.feature.profile.RegisterProfileViewModel
import id.rifqipadisiliwangi.tokopaerbe.presentation.feature.register.RegisterViewModel
import id.rifqipadisiliwangi.tokopaerbe.presentation.feature.review.ReviewViewModel
import id.rifqipadisiliwangi.tokopaerbe.presentation.feature.store.StoreListViewModel
import id.rifqipadisiliwangi.tokopaerbe.presentation.feature.wishlist.WishlistViewModel
import id.rifqipadisiliwangi.tokopaerbe.utils.AssetWrapperApp
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModules {


    private val viewModelModule = module {
        viewModelOf(::OnboardingViewModel)
        viewModelOf(::DashboardViewModel)
        viewModelOf(::WishlistViewModel)
        viewModelOf(::StoreListViewModel)
        viewModelOf(::LoginViewModel)
        viewModelOf(::RegisterViewModel)
        viewModelOf(::RegisterProfileViewModel)
        viewModelOf(::HomeViewModel)
        viewModelOf(::StoreListViewModel)
        viewModelOf(::DetailViewModel)
        viewModelOf(::ReviewViewModel)
        viewModelOf(::CartViewModel)
        viewModelOf(::PaymentViewModel)
        viewModelOf(::CheckoutViewModel)
        viewModelOf(::NotificationViewModel)
    }

    private val utilsModule = module {
        single { AssetWrapperApp(androidContext()) }
    }

    val modules: List<Module> = listOf(
        viewModelModule,
        utilsModule,
    )
}
