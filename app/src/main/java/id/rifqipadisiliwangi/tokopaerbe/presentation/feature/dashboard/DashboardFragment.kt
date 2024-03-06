package id.rifqipadisiliwangi.tokopaerbe.presentation.feature.dashboard

import android.annotation.SuppressLint
import androidx.navigation.fragment.findNavController
import id.rifqipadisiliwangi.tokopaerbe.R
import id.rifqipadisiliwangi.tokopaerbe.databinding.FragmentDashboardBinding
import id.rifqipadisiliwangi.tokopaerbe.utils.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment :
   BaseFragment<FragmentDashboardBinding, DashboardViewModel>(FragmentDashboardBinding::inflate){

    override val viewModel: DashboardViewModel by viewModel()

    override fun initView() {
        with(binding){
            tvId.text = getString(R.string.id)
            tvEn.text = getString(R.string.en)
            tvLight.text = getString(R.string.string_light)
            tvDark.text = getString(R.string.string_dark)
            btnLogout.text = getString(R.string.string_keluar)

        }
    }

    override fun initListener() {
        with(binding){
            viewModel.appThemeLiveData.observe(viewLifecycleOwner){ isActive ->
                swDarkmode.isChecked = isActive
            }
            viewModel.appLocaleLiveData.observe(viewLifecycleOwner){ isActive ->
                swLanguage.isChecked = isActive
            }
            swDarkmode.setOnCheckedChangeListener { _, isChecked ->
                viewModel.setThemeDarkmode(isChecked)
            }
            swLanguage.setOnCheckedChangeListener { _, isChecked ->
                viewModel.setLocale(isChecked)
            }
            btnLogout.setOnClickListener {
                logOut()
            }
        }
    }


    @SuppressLint("CommitTransaction")
    private fun logOut(){
        viewModel.deleteData()
        activity?.supportFragmentManager?.findFragmentById(R.id.container_navigation)?.findNavController()?.navigate(R.id.action_homeFragment_to_loginFragment)
    }

}