package hu.gde.marathon

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.RemoteException
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Entity
import androidx.room.PrimaryKey
import hu.gde.marathon.databinding.ActivityMainBinding
import hu.gde.marathon.databinding.RowNameBinding
import java.util.UUID

//        val names = mutableListOf(
//            Name(UUID.randomUUID().toString(),"Peter", "Kovacs"),
//            Name(UUID.randomUUID().toString(),"Akos", "Nagy"),
//            Name(UUID.randomUUID().toString(),"Zoltan","Kiss")
//            )

class MainActivity : AppCompatActivity() {

        lateinit var binding : ActivityMainBinding
        lateinit var adapter : NameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)

        val db:AppDatabase = AppDatabase.buildDatabase(applicationContext)      //itt hozzuk letre az application szintu contextet az adatbazishoz, amit hasznalni fogunk

        setContentView(binding.root)

        binding.Button.setOnClickListener {

            val name = Name(
                id = UUID.randomUUID().toString(),
                firstName = binding.fistNameInput.text.toString(),
                lastName = binding.lastNameInput.text.toString()
            )
            db.nameDao().insert(name)
            adapter.submitList(db.nameDao().getAll())

        }

        binding.Delete.setOnClickListener {
//            names.removeAt(0)
            adapter.submitList(db.nameDao().getAll())
        }

        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) //layout kiosztas

        adapter = NameAdapter(
            onClick = { name ->
//                Toast.makeText(this, name.lastName, Toast.LENGTH_SHORT).show()  // Toast messageben jön fel az adat
            val intent = Intent(this@MainActivity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.key_id,name.id)
            startActivity(intent)
            }
        )

        binding.recyclerView.adapter = adapter // az adapter segiti az adatok populaciojat, ebben hatarozzuk meg, hogy milyen adatok legyenek lathatoak

        adapter.submitList(db.nameDao().getAll())

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
@Entity(tableName = "names")

class Name (
    @PrimaryKey val id: String,
    val firstName: String,
    val lastName: String
)

class NameAdapter(val onClick: (Name) -> Unit,
    ):RecyclerView.Adapter<NameAdapter.NameViewHolder>(){  //ez a saját adapterünk a gördülőlistához. A ViewHolder tartalmazza majd az adott listaelemet

    private var items : List<Name> = listOf()

    class NameViewHolder(val binding:RowNameBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NameViewHolder {  //a képernyőre kerülő sorelemek rendereléséért felelős. Addig renderel, amíg ki nem generál elég containert, utána átadja a szerepet az onBindViewHolder-nek
        val binding = RowNameBinding.inflate(LayoutInflater.from(parent.context)) //Ezzel hozzuk létre a binding-ot, amire később lehet hivatkozni, mint a onCreate függvényében
        return NameViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size  // ez fogja megmondani, hogy hány elemet kell megjeleníteni az adapternek

    override fun onBindViewHolder(holder: NameViewHolder, position: Int) {  // Adott pozíción lévő elem értékét betölti a layoutba
        val item = items[position]
        holder.binding.root.setOnClickListener {
            onClick.invoke(item)
        }
        holder.binding.firstName.text = item.firstName
        holder.binding.lastName.text = item.lastName
    }
    fun submitList(data : List<Name>) {  //ezzel a függvénnyel adjuk hozzá a változónkat (lista)
        this.items = data
        notifyDataSetChanged()   //kell egy notify az adapternek, hogy megváltoztak az adatok, és frissítsen
    }

}