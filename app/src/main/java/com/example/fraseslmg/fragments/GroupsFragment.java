package com.example.fraseslmg.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.fraseslmg.R;
import com.example.fraseslmg.adapters.GroupsAdapter;
import com.example.fraseslmg.objetos.Groups;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupsFragment extends Fragment implements View.OnClickListener {

    private View root;
    ListView listViewGroups;
    List<Groups> list;

    public GroupsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_groups, container, false);

        listViewGroups = root.findViewById(R.id.ListViewGroup);

        GroupsAdapter adapter = new GroupsAdapter(getContext(), GetData());
        listViewGroups.setAdapter(adapter);
        listViewGroups.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Groups g = list.get(position);
            }
        });

        return root;
    }

    @Override
    public void onClick(View v) {

        Button b = (Button) v;

        switch (b.getId()){
            case R.id.btn_add:
                create_group();
                break;
        }

    }

    private void create_group() {
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
        dialogo1.setTitle("Añadir un nuevo grupo");
        dialogo1.setMessage("Eligue el nombre del nuevo grupo");
        final EditText input = new EditText(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        dialogo1.setView(input);
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Añadir", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                if(input.getText()==null){
                    input.setError("Introduzca el nombre");
                }else{
                    //Add GROUP;
                }
            }
        });
    }

    private List<Groups> GetData() {
        list = new ArrayList<>();
        list.add(new Groups("La Mosca Gau", "La mejor cuadrilla", R.drawable.ic_child_care_black_24dp));
        list.add(new Groups("Tu Madre", "La gorda", R.drawable.ic_child_care_black_24dp));
        list.add(new Groups("Pap puto negro", "Jajajaj ya tu sabe", R.drawable.ic_child_care_black_24dp));
        list.add(new Groups("Delirius papu", "papuuuuuu", R.drawable.ic_child_care_black_24dp));
        list.add(new Groups("KLK", "kukuxclan", R.drawable.ic_child_care_black_24dp));
        list.add(new Groups("motherfocker", "la madre de la foca", R.drawable.ic_child_care_black_24dp));
        list.add(new Groups("Pap puto negro", "Jajajaj ya tu sabe", R.drawable.ic_child_care_black_24dp));
        list.add(new Groups("Delirius papu", "papuuuuuu", R.drawable.ic_child_care_black_24dp));
        list.add(new Groups("KLK", "kukuxclan", R.drawable.ic_child_care_black_24dp));
        list.add(new Groups("motherfocker", "la madre de la foca", R.drawable.ic_child_care_black_24dp));
        return list;
    }
}
