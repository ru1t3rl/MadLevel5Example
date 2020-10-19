package tech.tucano.madlevel5example

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import tech.tucano.madlevel5example.databinding.ActivityMainBinding
import tech.tucano.madlevel5example.databinding.FragmentAddReminderBinding

class AddReminderFragment : Fragment() {
    private var _binding: FragmentAddReminderBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ReminderViewModel by viewModels()

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
            viewModel.insertReminder(Reminder(reminderText))

            findNavController().popBackStack()
        } else {
            Toast.makeText(
                activity,
                R.string.not_valid_reminder, Toast.LENGTH_SHORT
            ).show()
        }
    }
}