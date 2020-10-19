package tech.tucano.madlevel4example

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import tech.tucano.madlevel4example.databinding.ActivityMainBinding
import tech.tucano.madlevel4example.databinding.FragmentAddReminderBinding


const val REQ_REMINDER_KEY = "req_reminder"
const val BUNDLE_REMINDER_KEY = "bundle_reminder"

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AddReminderFragment : Fragment() {
    private var _binding: FragmentAddReminderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddReminderBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAddReminder.setOnClickListener{
            onAddReminder()
        }
    }

    private fun onAddReminder() {
        val reminderText = binding.tpReminderName.text.toString()
        if(reminderText.isNotBlank()){
            setFragmentResult(REQ_REMINDER_KEY, bundleOf(Pair(BUNDLE_REMINDER_KEY, reminderText)))

            findNavController().popBackStack()
        } else {
            Toast.makeText(
                activity,
                R.string.not_valid_reminder, Toast.LENGTH_SHORT
            ).show()
        }
    }
}