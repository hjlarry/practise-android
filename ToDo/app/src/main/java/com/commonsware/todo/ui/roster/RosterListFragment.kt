package com.commonsware.todo.ui.roster

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.commonsware.todo.R
import com.commonsware.todo.RosterAdapter
import com.commonsware.todo.databinding.TodoRosterBinding
import com.commonsware.todo.repo.FilterMode
import com.commonsware.todo.repo.ToDoModel
import com.commonsware.todo.ui.ErrorDialogFragment
import com.commonsware.todo.ui.ErrorScenario
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

class RosterListFragment : Fragment() {
    private val TAG = "ToDoListFragment"
    private val motor: RosterMotor by viewModel()
    private var binding: TodoRosterBinding? = null
    private val menuMap = mutableMapOf<FilterMode, MenuItem>()
    private val createDoc =
        registerForActivityResult(ActivityResultContracts.CreateDocument()) { motor.saveReport(it) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View = TodoRosterBinding.inflate(inflater, container, false).also { binding = it }.root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.actions_roster, menu)
        menuMap.apply {
            put(FilterMode.ALL, menu.findItem(R.id.all))
            put(FilterMode.COMPLETED, menu.findItem(R.id.completed))
            put(FilterMode.OUTSTANDING, menu.findItem(R.id.outstanding))
        }
        menuMap[motor.states.value.filterMode]?.isChecked = true

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = RosterAdapter(
            layoutInflater,
            onCheckboxToggle = { motor.save(it.copy(isCompleted = !it.isCompleted)) },
            onRowClick = ::display
        )
        binding?.items?.apply {
            setAdapter(adapter)
            layoutManager = LinearLayoutManager(context)

            addItemDecoration(
                DividerItemDecoration(
                    activity, DividerItemDecoration.VERTICAL
                )
            )
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            motor.states.collect { state ->
                adapter.submitList(state.items)
                binding?.apply {
                    progressBar.visibility = if (state.isLoaded) View.GONE else View.VISIBLE
                    when {
                        state.items.isEmpty() && state.filterMode == FilterMode.ALL -> {
                            empty.visibility = View.VISIBLE
                            empty.setText(R.string.msg_empty)
                        }
                        state.items.isEmpty() -> {
                            empty.visibility = View.VISIBLE
                            empty.setText(R.string.msg_empty_filtered)
                        }
                        else -> empty.visibility = View.GONE
                    }
                }
                menuMap[state.filterMode]?.isChecked = true
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            motor.navEvents.collect { nav ->
                when (nav) {
                    is Nav.ViewReport -> viewReport(nav.doc)
                    is Nav.ShareReport -> shareReport(nav.doc)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            motor.errorEvents.collect { error ->
                when (error) {
                    ErrorScenario.Import -> handleImportError()
                }
            }
        }

        findNavController().getBackStackEntry(R.id.rosterListFragment)
            .savedStateHandle
            .getLiveData<ErrorScenario>(ErrorDialogFragment.KEY_RETRY)
            .observe(viewLifecycleOwner) { retryScenario ->
                when (retryScenario) {
                    ErrorScenario.Import -> {
                        clearImportError()
                        motor.importItems()
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
            R.id.add -> {
                add()
                return true
            }
            R.id.all -> {
                item.isChecked = true
                motor.load(FilterMode.ALL)
                return true
            }
            R.id.completed -> {
                item.isChecked = true
                motor.load(FilterMode.COMPLETED)
                return true
            }
            R.id.outstanding -> {
                item.isChecked = true
                motor.load(FilterMode.OUTSTANDING)
                return true
            }
            R.id.save -> {
                saveReport()
                return true
            }
            R.id.share -> {
                motor.shareReport()
                return true
            }
            R.id.importItems -> {
                motor.importItems()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun display(model: ToDoModel) {
        findNavController().navigate(RosterListFragmentDirections.displayModel(model.id))
    }

    private fun add() {
        findNavController().navigate(RosterListFragmentDirections.createModel(null))
    }

    private fun saveReport() {
        createDoc.launch("report.html")
    }

    private fun safeStartActivity(intent: Intent) {
        try {
            startActivity(intent)
        } catch (t: Throwable) {
            Log.e(TAG, "Exception starting $intent", t)
            Toast.makeText(requireActivity(), R.string.oops, Toast.LENGTH_SHORT).show()
        }
    }

    private fun viewReport(uri: Uri) {
        safeStartActivity(
            Intent(
                Intent.ACTION_VIEW, uri
            ).setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        )
    }

    private fun shareReport(doc: Uri) {
        safeStartActivity(
            Intent(Intent.ACTION_SEND).setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                .setType("text/html").putExtra(Intent.EXTRA_STREAM, doc)
        )
    }

    private fun handleImportError() {
        findNavController().navigate(
            RosterListFragmentDirections.showError(
                getString(R.string.import_error_title),
                getString(R.string.import_error_message),
                ErrorScenario.Import
            )
        )
    }

    private fun clearImportError() {
        findNavController().getBackStackEntry(R.id.rosterListFragment).savedStateHandle.set(
            ErrorDialogFragment.KEY_RETRY,
            ErrorScenario.None
        )
    }
}