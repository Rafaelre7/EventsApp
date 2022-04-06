package com.example.eventosapp.view.events

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eventosapp.R
import com.example.eventosapp.databinding.EventsFragmentBinding
import com.example.eventosapp.utils.NetworkState
import com.example.eventosapp.view.events.adapter.EventAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventsFragment : Fragment(), EventAdapter.EventItemListener {

    private lateinit var binding: EventsFragmentBinding
    private val viewModel: EventsViewModel by viewModels()
    private lateinit var adapter: EventAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = EventsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        if (isConnected) {
            setupRecyclerView()
            setupObservers()
        }else{
            Toast.makeText(requireContext(), "Sem conexão", Toast.LENGTH_SHORT).show()
        }
    }

    fun setupRecyclerView() {
        adapter = EventAdapter(this)
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerview.adapter = adapter
    }

    fun setupObservers() {
        viewModel.events.observe(viewLifecycleOwner) {
            when (it.status) {
                NetworkState.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()) adapter.setItems(ArrayList(it.data))
                }
                NetworkState.Status.ERROR ->
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                NetworkState.Status.LOADING ->
                    binding.progressBar.visibility = View.VISIBLE
            }
        }
    }


    override fun onClickedEvent(eventId: Int) {
        findNavController().navigate(
            R.id.action_eventsFragment_to_detail_fragment,
            bundleOf("id" to eventId)
        )
    }

}