// File: app/src/main/java/com/example/myhabit/home/HomeActivity.kt
package com.example.myhabit.home

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myhabit.data.Habit
import com.example.myhabit.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val TAG = "MyHabitHome"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Display welcome message
        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_LONG).show()
            finish()
            return
        }
        binding.welcomeText.text = "Welcome, ${user.email}!"

        // Initialize database reference with explicit URL
        // Replace with your actual Project ID and region
        val databaseUrl = "https://myhabit-847e9-default-rtdb.asia-southeast1.firebasedatabase.app"
        val dbRef = FirebaseDatabase.getInstance(databaseUrl)
            .getReference("habits")
            .child(user.uid)

        // Setup RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        loadHabits(dbRef)

        // Save Habit on button click
        binding.saveHabitBtn.setOnClickListener {
            val habitName = binding.habitName.text.toString().trim()
            if (habitName.isEmpty()) {
                Toast.makeText(this, "Masukkan nama habit", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val habit = Habit(habitName, System.currentTimeMillis())
            Log.d(TAG, "Pushing habit: $habit")
            Toast.makeText(this, "Saving habit...", Toast.LENGTH_SHORT).show()

            dbRef.push().setValue(habit)
                .addOnSuccessListener {
                    Log.d(TAG, "Habit saved: $habit")
                    Toast.makeText(this, "Habit Saved", Toast.LENGTH_SHORT).show()
                    binding.habitName.text?.clear()
                    loadHabits(dbRef)
                }
                .addOnFailureListener { e ->
                    Log.e(TAG, "Error saving habit", e)
                    Toast.makeText(
                        this,
                        "Failed to save: ${e.localizedMessage}",
                        Toast.LENGTH_LONG
                    ).show()
                }
        }
    }

    private fun loadHabits(dbRef: com.google.firebase.database.DatabaseReference) {
        dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val habits = snapshot.children.mapNotNull { it.getValue(Habit::class.java) }
                Log.d(TAG, "Loaded habits: $habits")
                binding.recyclerView.adapter = HabitAdapter(habits)
                Toast.makeText(
                    this@HomeActivity,
                    "You have ${habits.size} habit(s)",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Failed to load habits", error.toException())
            }
        })
    }
}
