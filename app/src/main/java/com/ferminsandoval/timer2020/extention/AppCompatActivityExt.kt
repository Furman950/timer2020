package com.ferminsandoval.timer2020.extention

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
inline fun <reified T : ViewModel> AppCompatActivity.createViewModel(crossinline factory: () -> T): T =
    T::class.java.let { clazz ->
        ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                if (modelClass == clazz) {
                    return factory() as T
                }
                throw IllegalArgumentException("Unexpected argument: $modelClass")
            }
        }).get(clazz)
    }


//@Suppress("UNCHECKED_CAST")
//inline fun <reified T : ViewModel> AppCompatActivity.activityViewModels(
//    crossinline creator: () -> T
//): Lazy<T> = object : Lazy<T> {
//    private var _viewModel: T? = null
//
//    override val value: T
//        get() {
//            if (_viewModel == null) {
//                val vm = getViewModel().get(T::class.java)
//                _viewModel = vm
//            }
//
//            return _viewModel as T
//        }
//
//    override fun isInitialized(): Boolean = _viewModel != null
//
//    fun getViewModel(): ViewModelProvider = T::class.java.let {
//        ViewModelProvider(this@activityViewModels, object : ViewModelProvider.Factory {
//            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//                if (modelClass == it) {
//                    return creator.invoke() as T
//                }
//                throw IllegalArgumentException("Unexpected argument: $modelClass")
//            }
//        })
//    }
//}


@Suppress("UNCHECKED_CAST")
inline fun <reified T : ViewModel> AppCompatActivity.activityViewModels(
    crossinline creator: () -> T
): Lazy<T> = object : Lazy<T> {
    private var _viewModel: T? = null

    override val value: T
        get() {
            if (_viewModel == null) {
                _viewModel = getViewModel()
            }

            return _viewModel as T
        }

    override fun isInitialized(): Boolean = _viewModel != null

    fun getViewModel(): T = T::class.java.let {
        ViewModelProvider(this@activityViewModels, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                if (modelClass == it) {
                    return creator() as T
                }
                throw IllegalArgumentException("Unexpected argument: $modelClass")
            }
        }).get(it)
    }
}

