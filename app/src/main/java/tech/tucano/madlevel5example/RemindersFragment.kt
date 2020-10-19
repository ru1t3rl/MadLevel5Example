package tech.tucano.madlevel5example

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_reminders.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import tech.tucano.madlevel5example.databinding.FragmentRemindersBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class RemindersFragment : Fragment() {
    private val reminders = arrayListOf<Reminder>()
    private val reminderAdapter = ReminderAdapter(reminders)

    private val viewModel: ReminderViewModel by viewModels()
    private lateinit var binding: FragmentRemindersBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRemindersBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRv()

        observeAddReminderResult()
    }

    private fun initRv(){
        binding.rvReminders.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.rvReminders.adapter = reminderAdapter
        binding.rvReminders.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

        createItemTouchHelper().attachToRecyclerView(rvReminders)
    }

    private fun observeAddReminderResult() {
        viewModel.reminders.observe(viewLifecycleOwner, Observer {
            reminders ->
            this.reminders.clear()
            this.reminders.addAll(reminders)
            reminderAdapter.notifyDataSetChanged()
        })
    }

    private fun createItemTouchHelper(): ItemTouchHelper {
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                CoroutineScope(Dispatchers.Main).launch {
                    withContext(Dispatchers.IO) {
                        viewModel.deleteReminder(reminders[position])
                    }
                }
            }
        }
        return ItemTouchHelper(callback)
    }
}