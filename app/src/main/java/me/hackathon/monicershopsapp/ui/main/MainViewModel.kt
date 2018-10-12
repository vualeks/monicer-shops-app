package me.hackathon.monicershopsapp.ui.main

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel() {

  val shopTitle = ObservableField<String>()

}