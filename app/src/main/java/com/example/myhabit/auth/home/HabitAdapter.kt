package com.example.myhabit.home
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myhabit.data.Habit
import com.example.myhabit.databinding.ItemHabitBinding

class HabitAdapter(private val list:List<Habit>):RecyclerView.Adapter<HabitAdapter.VH>(){
    inner class VH(val b:ItemHabitBinding):RecyclerView.ViewHolder(b.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=VH(
        ItemHabitBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    )
    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.b.habitName.text=list[position].name
        holder.b.habitTime.text=java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(list[position].timestamp)
    }
    override fun getItemCount()=list.size
}