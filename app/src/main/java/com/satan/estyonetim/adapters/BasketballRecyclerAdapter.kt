package com.satan.estyonetim.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.satan.estyonetim.adminhomeviews.CreateBasketballGameFragment.Companion.currentPersonCount
import com.satan.estyonetim.databinding.BasketballActivityRowBinding
import com.satan.estyonetim.model.Basketball
import com.squareup.picasso.Picasso

class BasketballRecyclerAdapter(var activityList : ArrayList<Basketball> ) :
    RecyclerView.Adapter<BasketballRecyclerAdapter.BasketballViewHolder>() {

    private  var database = Firebase.firestore


    open class BasketballViewHolder(val binding : BasketballActivityRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketballViewHolder {
        val binding = BasketballActivityRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return BasketballViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BasketballViewHolder, position: Int) {
        holder.binding.activityDate.text = activityList[position].gameDay
        holder.binding.activityTime.text = "${activityList[position].gameHour} : ${activityList[position].gameMinute}"
        holder.binding.totalPerson.text = activityList[position].personCount
        holder.binding.currentPersonCount.text = currentPersonCount.toString()
        holder.binding.activityStatus.text = activityList[position].gameStatus

        Picasso.get().load(activityList[position].gamePhotoUrl).into(holder.binding.basketballTypeImage)


        val toJoinCheckBox = holder.binding.toJoinActivityCheckBox
        val quitCheckBox = holder.binding.quitActivityCheckBox

            holder.binding.verificationButton.setOnClickListener {
                if (toJoinCheckBox.isChecked) {
                    currentPersonCount++
                    database.collection("BasketballActivity").document("XFULF4cDYUkpL1M0LGmk")
                        .update("currentPersonCount", currentPersonCount).addOnSuccessListener {

                        }

                } else if (quitCheckBox.isChecked) {
                    currentPersonCount--
                    database.collection("BasketballActivity").document("XFULF4cDYUkpL1M0LGmk")
                        .update("currentPersonCount", currentPersonCount).addOnSuccessListener {

                        }

                }
            }

        }

    override fun getItemCount(): Int {
        return activityList.size
    }


    }










