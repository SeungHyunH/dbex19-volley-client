package kr.ac.kumoh.s20161271.gunplaapplication

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest

class GunplaViewModel(application: Application): AndroidViewModel(application) {
    companion object {
        const val QUEUE_TAG = "VolleyRequest"
    }

    private lateinit var mQueue: RequestQueue
    data class Mechanic (
        val id: Int,
        val name: String,
        val model: String,
        val manufacturer: String,
        val armor: String,
        val height: Double,
        val weight: Double
    )

    val list = MutableLiveData<ArrayList<Mechanic>>()
    private val gunpla = ArrayList<Mechanic>()

    init {
        list.value = gunpla
        mQueue = VolleyRequest.getInstance(application).requestQueue
    }

    override fun onCleared() {
        super.onCleared()
        mQueue.cancelAll(QUEUE_TAG)
    }

    fun getGunpla(i: Int) = gunpla[i]

    fun getSize() = gunpla.size

    fun requestMechanic() {
        val url = "https://dbex-volley-server-cnzqf.run.goorm.io/gunpladb/mechanic/"

        val request = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            {
                Toast.makeText(getApplication(), it.toString(), Toast.LENGTH_LONG).show()
                gunpla.clear()
                list.value = gunpla
            },
            {
                Toast.makeText(getApplication(), it.toString(), Toast.LENGTH_LONG).show()
            }
        )

        request.tag = QUEUE_TAG
        mQueue.add(request)
    }
}