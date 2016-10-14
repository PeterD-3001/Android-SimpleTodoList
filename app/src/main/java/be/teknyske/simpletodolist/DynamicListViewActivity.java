package be.teknyske.simpletodolist;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DynamicListViewActivity extends AppCompatActivity {

    private EditText item;
    private ImageButton addButtonReference;
    private ListView myDynamicListView;
    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_list_view);

        //get References to items from UI
        item = (EditText) findViewById(R.id.itemEditText);
        addButtonReference = (ImageButton) findViewById(R.id.addItemButton);
        myDynamicListView = (ListView) findViewById(R.id.itemsListView);

        // create list and add some test-items
        list = new ArrayList<>();
        list.add("Eten");
        list.add("Werken");
        list.add("Slapen");

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, list);
        myDynamicListView.setAdapter(adapter);

        // add item
        addButtonReference.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String todoItem = item.getText().toString();
                if (todoItem.length() > 0)
                {
                    list.add(item.getText().toString());
                    adapter.notifyDataSetChanged();
                    item.setText("");
                }
            }
        });

        // delete item
        myDynamicListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long arg3)
            {
                list.remove(position);
                adapter.notifyDataSetChanged();
                return true;
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        saveList();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadList();
    }

    private void loadList(){
        SharedPreferences preferences =  getPreferences(MODE_PRIVATE);
        list.clear();
        list.addAll(preferences.getStringSet("list",new HashSet<String>()));
        adapter.notifyDataSetChanged();

    }

    private void saveList()
    {
        SharedPreferences preferences =  getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Set<String> values = new HashSet<>();
        values.addAll(list);
        editor.putStringSet("list", values);
        editor.commit();
    }

}
