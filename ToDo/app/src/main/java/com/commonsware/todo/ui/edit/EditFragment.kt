package com.commonsware.todo.ui.edit

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.commonsware.todo.R
import com.commonsware.todo.databinding.TodoEditBinding
import com.commonsware.todo.repo.ToDoModel
import com.commonsware.todo.ui.SingleModelMotor
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class EditFragment : Fragment() {
    private val args: EditFragmentArgs by navArgs()
    private var binding: TodoEditBinding? = null
    private val motor: SingleModelMotor by viewModel { parametersOf(args.modelId) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = TodoEditBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.actions_edit, menu)
        menu.findItem(R.id.delete).isVisible = args.modelId != null

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        motor.getModel()?.let {
            binding?.apply {
                isCompleted.isChecked = it.isCompleted
                desc.setText(it.description)
                notes.setText(it.notes)
            }
        }

    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save -> {
                save()
                return true
            }
            R.id.delete -> {
                delete()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun save() {
        binding?.apply {
            val model = motor.getModel()
            val edited = model?.copy(
                description = desc.text.toString(),
                isCompleted = isCompleted.isChecked,
                notes = notes.text.toString()
            ) ?: ToDoModel(
                description = desc.text.toString(),
                isCompleted = isCompleted.isChecked,
                notes = notes.text.toString()
            )
            edited.let { motor.save(it) }
        }
        navToDisplay()
    }

    private fun delete() {
        val model = motor.getModel()
        model?.let { motor.delete(it) }
        navToList()
    }

    private fun navToDisplay() {
        findNavController().popBackStack()
    }

    private fun navToList() {
        findNavController().popBackStack(R.id.rosterListFragment, false)
    }
}