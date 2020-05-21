package com.ahmad.softxperttask.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmad.softxperttask.R;
import com.ahmad.softxperttask.model.Car;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CarsRecyclerAdapter extends RecyclerView.Adapter<CarsRecyclerAdapter.CarsViewHolder> {

    public ArrayList<Car> cars = new ArrayList<>();

    @NonNull
    @Override
    public CarsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CarsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CarsViewHolder holder, int position) {
        Car car = cars.get(position);
        holder.carBrand.setText(car.getBrand());
        holder.carIsUsed.setText(car.getUsed()? "USED" : "NEW");
        holder.carConstructionYear.setText(car.getConstructionYear());
        Picasso.get()
                .load(car.getImageUrl())
                .centerCrop()
                .resize(150,150)
                .into(holder.carImageView);
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    public ArrayList<Car> getCars() {
        return cars;
    }

    public void addToCars(Car car){
        cars.add(car);
        notifyDataSetChanged();
    }

    public void clearCars(){
        cars.clear();
        notifyDataSetChanged();
    }

    public class CarsViewHolder extends RecyclerView.ViewHolder{
        TextView carBrand;
        TextView carIsUsed;
        TextView carConstructionYear;
        ImageView carImageView;

        CarsViewHolder(View itemView){
            super(itemView);
            carBrand = itemView.findViewById(R.id.tv_car_name);
            carIsUsed = itemView.findViewById(R.id.tv_car_used);
            carConstructionYear = itemView.findViewById(R.id.tv_car_year);
            carImageView = itemView.findViewById(R.id.iv_car);
        }
    }
}
