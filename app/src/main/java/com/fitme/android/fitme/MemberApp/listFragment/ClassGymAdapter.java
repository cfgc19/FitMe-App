package com.fitme.android.fitme.MemberApp.listFragment;

import android.app.Activity;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.fitme.android.fitme.com.fitme.android.fitme.model.Athlete;
import com.fitme.android.fitme.com.fitme.android.fitme.model.Trainer;
import java.util.ArrayList;

import com.fitme.android.fitme.R;
import com.fitme.android.fitme.com.fitme.android.fitme.model.GymClass;
import java.util.List;

public class ClassGymAdapter extends ArrayAdapter<GymClass> {

  private Object object;

  public ClassGymAdapter(Activity context, ArrayList<GymClass> gymClasses, Object user) {
    super(context, 0, gymClasses);
    object = user;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    View listItemView = convertView;

    if (listItemView == null) {

        listItemView = LayoutInflater.from(getContext()).inflate(
              R.layout.list_item, parent, false);
    }

    GymClass currentClass = getItem(position);
    List<GymClass> gymClassList;
    Log.i("GymClass ->", currentClass.toString());

    if(object instanceof Athlete){
      gymClassList = ((Athlete) object).getSchedule();
    }
    else{
      gymClassList = ((Trainer) object).getGymClasses();
    }

    listItemView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorWhite));
    if(!gymClassList.isEmpty()) {
      for (int i = 0; i < gymClassList.size(); i++) {
        if (gymClassList.get(i)
            .getIdClass()
            .equals(currentClass.getIdClass())) {
          listItemView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorSecondaryLightTransparent));
        }
      }
    }

    TextView scheduleClass = (TextView) listItemView.findViewById(R.id.schedule_class);
    scheduleClass.setText(currentClass.getStartTime() + "-" + currentClass.getFinishTime());

    TextView nameClass = (TextView) listItemView.findViewById(R.id.name_class);
    nameClass.setText(currentClass.getType());

    ImageView image = listItemView.findViewById(R.id.image_class);
    String[] modalities = getContext().getResources().getStringArray(R.array.modalities);
    TypedArray imagesModalities = getContext().getResources().obtainTypedArray(R.array.modalities_images);

    for(int i=0; i<modalities.length; i++){
      if (modalities[i].equals(currentClass.getType())){
        image.setImageResource(imagesModalities.getResourceId(i,-1));
        break;
      }
    }
    imagesModalities.recycle();

    return listItemView;
  }
}
