package com.commonsware.todo.ui.display

import android.os.Bundle
import android.text.format.DateUtils
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.commonsware.todo.R
import com.commonsware.todo.databinding.TodoDisplayBinding
import com.commonsware.todo.ui.SingleModelMotor
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DisplayFragment : Fragment() {
    private val args: DisplayFragmentArgs by navArgs()
    private var binding: TodoDisplayBinding? = null
    private val motor: SingleModelMotor by viewModel { parametersOf(args.modelId) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.actions_display, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = TodoDisplayBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            motor.states.collect { state ->
                state.item?.let {
                    binding?.apply {
                        completed.visibility = if (it.isCompleted) View.VISIBLE else View.GONE
                        desc.text = it.description
                        createdOn.text = DateUtils.getRelativeDateTimeString(
                            requireContext(),
                            it.createdOn.toEpochMilli(),
                            DateUtils.MINUTE_IN_MILLIS,
                            DateUtils.WEEK_IN_MILLIS,
                            0
                        )
                        notes.text = it.notes
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.edit -> {
                edit()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun edit() {
        findNavController().navigate(DisplayFragmentDirections.editModel(args.modelId))
    }
}