package hu.gde.marathon

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hu.gde.marathon.databinding.ActivityDetailBinding
import hu.gde.marathon.databinding.ActivityMainBinding

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding    // felvessy-k a bindingot

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater) // ;rt;ket adunk a bindingnak
        setContentView(binding.root)   //beallitjuk a contentet

        val id = intent.getStringExtra(key_id) ?: error("ID required!")
        val fn = intent.getStringExtra(key_first_name)
        val ln = intent.getStringExtra(key_last_name)

        val db = AppDatabase.buildDatabase(applicationContext)
        val name = db.nameDao().getOne(id)

        binding.id.text = id
        binding.firstName.text = name?.firstName
        binding.lastName.text = name?.lastName

    }

    companion object{
        const val key_id = "arg_id"
        const val key_first_name = "arg_fn"
        const val key_last_name = "arg_ln"
    }
}