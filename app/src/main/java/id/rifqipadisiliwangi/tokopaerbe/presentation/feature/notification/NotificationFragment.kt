package id.rifqipadisiliwangi.tokopaerbe.presentation.feature.notification

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import id.rifqipadisiliwangi.tokopaerbe.R
import id.rifqipadisiliwangi.tokopaerbe.databinding.FragmentNotificationBinding
import id.rifqipadisiliwangi.tokopaerbe.presentation.common.adapter.notification.NotificationAdapterItem
import id.rifqipadisiliwangi.tokopaerbe.utils.base.BaseFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationFragment : BaseFragment<FragmentNotificationBinding, NotificationViewModel>(FragmentNotificationBinding::inflate) {

    override val viewModel: NotificationViewModel by viewModel()

    override fun initView() {
        setUpListNotification()
        with(binding){
            menuToolbar.title = getString(R.string.string_notifikasi)
        }
    }

    override fun initListener() {
        with(binding) {
            menuToolbar.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun setUpListNotification(){
        with(binding){
            rvNotification.layoutManager = LinearLayoutManager(requireContext())
            val adapter = NotificationAdapterItem(
                onNotificationsClick = { notifEntity ->
                    if (!notifEntity.isChecked) {
                        viewModel.notifIsChecked(notifEntity.notifId, true)
                    } else {
                        // do nothing
                    }
                }
            )
            rvNotification.adapter = adapter
            rvNotification.itemAnimator?.changeDuration = 0
            lifecycleScope.launch {
                while (true) {
                    if (isActive) {
                        viewModel.getNotification().observe(viewLifecycleOwner) {
                            if (it?.isNotEmpty() == true) {
                                adapter.submitList(it.reversed())
                            }
                        }
                    }
                    if (!isActive) {
                        break
                    }
                    delay(1000)
                }
            }
        }
    }
}