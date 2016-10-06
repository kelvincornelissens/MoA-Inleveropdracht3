package com.dev.zerty.z_mail.Recycler;

import android.widget.Filter;

import com.dev.zerty.z_mail.Database.DBStudent;
import com.dev.zerty.z_mail.Students.Student;

import java.util.ArrayList;

public class CustomFilter extends Filter {

    MyRecyclerAdapter adapter;
    ArrayList<DBStudent> filterList;

    public CustomFilter(MyRecyclerAdapter adapter, ArrayList<DBStudent> filterList) {
        this.adapter = adapter;
        this.filterList = filterList;
    }

    //Filtering
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {

        //Results
        FilterResults results = new FilterResults();

        //Validation
        if(constraint != null && constraint.length() > 0 )
        {
            //Change to upper for consistancy
            constraint = constraint.toString().toUpperCase();
            ArrayList<DBStudent> filteredItems = new ArrayList<>();

            for (DBStudent i : filterList)
            {
                if((i.getFirstname().toUpperCase().startsWith(constraint.toString().toUpperCase())) || i.getPreposition().toUpperCase().startsWith(constraint.toString().toUpperCase()) ||
                        i.getLastname().toUpperCase().startsWith(constraint.toString().toUpperCase()) || i.getStudentnumber().toUpperCase().startsWith(constraint.toString().toUpperCase()))
                {
                    filteredItems.add(i);
                }
            }
            results.count = filteredItems.size();
            results.values = filteredItems;
            return results;
        }
        results.count = filterList.size();
        results.values = filterList;
        return results;
    }

    //Publish result
    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.students = (ArrayList<DBStudent>) results.values;
        adapter.notifyDataSetChanged();
    }
}
