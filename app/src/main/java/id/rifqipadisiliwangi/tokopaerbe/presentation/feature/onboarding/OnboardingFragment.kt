package id.rifqipadisiliwangi.tokopaerbe.presentation.feature.onboarding

import androidx.core.view.isGone
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import id.rifqipadisiliwangi.tokopaerbe.R
import id.rifqipadisiliwangi.tokopaerbe.databinding.FragmentOnboardingBinding
import id.rifqipadisiliwangi.tokopaerbe.presentation.common.adapter.onboarding.OnboardingAdapter
import id.rifqipadisiliwangi.tokopaerbe.utils.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class OnboardingFragment :
    BaseFragment<FragmentOnboardingBinding, OnboardingViewModel>(FragmentOnboardingBinding::inflate){

    private lateinit var vpOnboarding: ViewPager2
    private lateinit var adapter: OnboardingAdapter
    private lateinit var imageList: ArrayList<Int>
    private lateinit var tabs: TabLayout

    override val viewModel: OnboardingViewModel by viewModel()

    override fun initView() {
        setUpvp()
    }

    override fun initListener() {
        with(binding){
            tvNext.setOnClickListener {
                val nextIndex = vpOnboarding.currentItem + 1
                vpOnboarding.setCurrentItem(nextIndex, true)
            }
            tvSkipOnboarding.setOnClickListener {
                viewModel.setStateOnboarding(isActive = true)
                findNavController().navigate(R.id.action_onboardingFragment_to_loginFragment)
            }
            btnAuthentication.setOnClickListener {
                findNavController().navigate(R.id.action_onboardingFragment_to_registerFragment)
            }
            btnAuthentication.text = getString(R.string.string_gabung_sekarang)
            tvSkipOnboarding.text = getString(R.string.string_lewati)
            tvNext.text = getString(R.string.string_selanjutnya)
        }
    }
    private fun setUpvp() {
        vpOnboarding = binding.vpOnboarding
        tabs = binding.tabs

        imageList = arrayListOf(
            R.drawable.onboard_one,
            R.drawable.onboard_two,
            R.drawable.onboard_three
        )

        adapter = OnboardingAdapter(imageList)
        vpOnboarding.adapter = adapter

        TabLayoutMediator(
            tabs,
            vpOnboarding
        ) { tab, _ ->
            tab.customView = layoutInflater.inflate(R.layout.custom_tab_layout, null)
        }.attach()

        vpOnboarding.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    2 -> binding.tvNext.isGone = true
                    else -> binding.tvNext.isGone = false
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.vpOnboarding.unregisterOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {})
    }
}