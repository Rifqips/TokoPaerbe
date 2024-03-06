package id.rifqipadisiliwangi.tokopaerbe.presentation.feature.home

import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.window.layout.WindowMetricsCalculator
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.navigationrail.NavigationRailView
import id.rifqipadisiliwangi.tokopaerbe.R
import id.rifqipadisiliwangi.tokopaerbe.databinding.FragmentHomeBinding
import id.rifqipadisiliwangi.tokopaerbe.utils.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeViewModel>(FragmentHomeBinding::inflate){

    override val viewModel : HomeViewModel by viewModel()

    override fun initView() {
        setUpFragment()
        observeData()
        getData()
    }

    private fun getData() {
        viewModel.getUsernameLiveData()
    }

    private fun observeData() {
        if (view != null){
            viewModel.isUsername.observe(viewLifecycleOwner){ isUsername ->
                binding.menuToolbar.title = isUsername
            }
        }
    }

    override fun initListener() {
        with(binding) {
            menuToolbar.setOnMenuItemClickListener{ menu ->
                when(menu.itemId){
                    R.id.menuNotifikasi -> findNavController().navigate(R.id.action_homeFragment_to_notificationFragment)
                    R.id.menuCart -> findNavController().navigate(R.id.action_homeFragment_to_cartFragment)
                }
                true

            }
        }
    }
    private fun setUpFragment() {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.container_bottom_navigation) as NavHostFragment
        val navController = navHostFragment.navController
        val metrics = context?.let {
            WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(it)
        }
        val withDp = metrics?.bounds?.width()?.div(resources.displayMetrics.density)
        with(binding){
            if (withDp != null) {
                when {
                    withDp < 600f -> {
                        val bottomNav = binding.bottomNavigation as BottomNavigationView

                        bottomNav.setupWithNavController(navController)
                        bottomNav.setOnItemSelectedListener {
                            when (it.itemId) {
                                R.id.menuHome -> containerBottomNavigation.findNavController()
                                    .navigate(R.id.dashboardFragment)

                                R.id.menuStore -> containerBottomNavigation.findNavController()
                                    .navigate(R.id.storeFragment)

                                R.id.menuWishlist -> containerBottomNavigation.findNavController()
                                    .navigate(R.id.wishlistFragment)

                                R.id.menuTransaction -> containerBottomNavigation.findNavController()
                                    .navigate(R.id.transactionFragment)
                            }
                            true
                        }
                    }
                    withDp < 840f -> {
                        val navRail = binding.bottomNavigation as NavigationRailView
                        navRail.setupWithNavController(navController)
                        navRail.setOnItemSelectedListener {
                            when (it.itemId) {
                                R.id.menuHome -> containerBottomNavigation.findNavController()
                                    .navigate(R.id.dashboardFragment)

                                R.id.menuStore -> containerBottomNavigation.findNavController()
                                    .navigate(R.id.storeFragment)

                                R.id.menuWishlist -> containerBottomNavigation.findNavController()
                                    .navigate(R.id.wishlistFragment)

                                R.id.menuTransaction -> containerBottomNavigation.findNavController()
                                    .navigate(R.id.transactionFragment)
                            }
                            true
                        }
                    }
                    else -> {
                        val navDrawer = binding.bottomNavigation as NavigationView
                        navDrawer.setupWithNavController(navController)
                    }
                }
            }
        }
    }

}