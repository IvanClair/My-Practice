package ivan.personal.mypractice.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import ivan.personal.mypractice.router.MainRouter

class MainViewModel @ViewModelInject constructor(private val router: MainRouter) : ViewModel() {
}