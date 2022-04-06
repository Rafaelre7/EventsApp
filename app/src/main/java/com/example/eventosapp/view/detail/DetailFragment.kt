package com.example.eventosapp.view.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.eventosapp.data.entity.CheckIn
import com.example.eventosapp.data.entity.Event
import com.example.eventosapp.databinding.DetailFragmentBinding
import com.example.eventosapp.utils.NetworkState
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: DetailFragmentBinding
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getInt("id")?.let { viewModel.start(it) }
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.event.observe(viewLifecycleOwner) {
            when (it.status) {
                NetworkState.Status.SUCCESS -> {
                    bindEvent(it.data!!)
                    binding.progressBar.visibility = View.GONE
                }

                NetworkState.Status.ERROR ->
                    Toast.makeText(context, "Falha na conexão", Toast.LENGTH_SHORT).show()

                NetworkState.Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun bindEvent(event: Event) {
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
        val dateString = simpleDateFormat.format(event.date)
        binding.title.text = event.title
        Glide.with(binding.root).load(event.image).into(binding.imageview)
        binding.date.text = dateString
        binding.description.text = event.description
        binding.price.text = event.price.toString()

        doCheckInButton(event.id)
    }

    private fun doCheckInButton(id : Int) {


        binding.button.setOnClickListener {

            val nameCheckIn = binding.textFieldName.editText?.text.toString()
            val emailCheckIn = binding.textFieldEmail.editText?.text.toString()
            if (nameCheckIn.isNotEmpty() && emailCheckIn.isNotEmpty()) {
                viewModel.doCheckIn(CheckIn(id.toString(), nameCheckIn, emailCheckIn))
            }else{
                Toast.makeText(context, "É necessario preencher todos os campos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnCompartilhar.setOnClickListener {
            val msg = binding.description.text.toString()
            val whatsappIntent = Intent(Intent.ACTION_SEND)
            whatsappIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            whatsappIntent.type = "text/plain"
            whatsappIntent.setPackage("com.whatsapp")
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, msg)
            try {
                startActivity(whatsappIntent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


}