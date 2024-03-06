package id.rifqipadisiliwangi.tokopaerbe.presentation.feature.splash

import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.AnimationUtils
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import id.rifqipadisiliwangi.tokopaerbe.R
import id.rifqipadisiliwangi.tokopaerbe.databinding.FragmentSplashBinding
import id.rifqipadisiliwangi.tokopaerbe.presentation.feature.onboarding.OnboardingViewModel
import id.rifqipadisiliwangi.tokopaerbe.utils.base.BaseFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment :
    BaseFragment<FragmentSplashBinding, OnboardingViewModel>(FragmentSplashBinding::inflate){

    override val viewModel: OnboardingViewModel by viewModel()

    override fun initView() {
        getData()
        observeData()
        rotateView()
        translationYView()
        translationXView()
    }

    override fun initListener() {
        fadeView()
    }

    private fun getData() {
        viewModel.getToken()
    }
    private fun observeData() {
        viewModel.isUserToken.observe(viewLifecycleOwner){ isToken ->
            lifecycleScope.launch {
                delay(2000)
                when(isToken){
                    true -> findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
                    else ->  navigateController()
                }
            }
        }
    }

    private fun navigateController(){
        if (view != null){
            viewModel.appOnboardingLiveData.observe(viewLifecycleOwner){ isActive ->
                when(isActive){
                    true -> findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
                    else -> findNavController().navigate(R.id.action_splashFragment_to_onboardingFragment)
                }
            }
        }
    }

    private fun fadeView() {
        val animation = AnimationUtils.loadAnimation(requireActivity(), R.anim.fade_in)
        with(binding){
            ivYellow.startAnimation(animation)
            ivRed.startAnimation(animation)
            ivGreen.startAnimation(animation)
            ivBlue.startAnimation(animation)
            ivGreen.load(R.drawable.rounded_splash_green)
            ivYellow.load(R.drawable.rounded_splash_yellow)
            ivRed.load(R.drawable.rounded_splash_red)
            ivBlue.load(R.drawable.ic_splash)
        }
    }

    private fun rotateView(){
        val yellowAnimator = ObjectAnimator.ofFloat(
            binding.ivYellow,
            View.ROTATION,
            0f, -15f
        )
        val redAnimator = ObjectAnimator.ofFloat(
            binding.ivRed,
            View.ROTATION,
            0f, 30f
        )

        yellowAnimator.duration = 1000
        yellowAnimator.start()

        redAnimator.duration = 1000
        redAnimator.start()

    }
    private fun translationYView(){

        val yellowAnimator = ObjectAnimator.ofFloat(
            binding.ivYellow,
            View.TRANSLATION_Y,
            0f, -100f
        )
        val redAnimator = ObjectAnimator.ofFloat(
            binding.ivRed,
            View.TRANSLATION_Y,
            0f, -40f
        )
        val greenAnimator = ObjectAnimator.ofFloat(
            binding.ivGreen,
            View.TRANSLATION_Y,
            0f, -170f
        )

        yellowAnimator.duration = 1000
        yellowAnimator.start()

        redAnimator.duration = 1000
        redAnimator.start()

        greenAnimator.duration = 1000
        greenAnimator.start()

    }
    private fun translationXView(){

        val yellowAnimator = ObjectAnimator.ofFloat(
            binding.ivYellow,
            View.TRANSLATION_X,
            0f, -100f
        )
        val redAnimator = ObjectAnimator.ofFloat(
            binding.ivRed,
            View.TRANSLATION_X,
            0f, 100f
        )

        yellowAnimator.duration = 1000
        yellowAnimator.start()

        redAnimator.duration = 1000
        redAnimator.start()

    }

}