package org.sagemath.droid;

import java.util.LinkedList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

/**
 * @author Rasmi.Elasmar
 * @author Ralf.Stephan
 * 
 */
public class CellListAdapter extends ArrayAdapter<CellData> {
	private final Context context;

	private LinkedList<CellData> cells;

	public CellListAdapter(Context context, LinkedList<CellData> cells) {
		super(context, R.layout.cell_list_item, cells);
		this.context = context;
		this.cells = cells;
	}

	static class ViewHolder {
		protected TextView titleView;
		protected TextView descriptionView;
		protected CheckBox favorite;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View item;
		TextView titleView;
		TextView descriptionView;
		final CheckBox favorite;
		final int my_position = position;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			item = inflater.inflate(R.layout.cell_list_item, parent, false);
			titleView = (TextView) item.findViewById(R.id.cell_title);
			descriptionView = (TextView) item
					.findViewById(R.id.cell_description);
			favorite = (CheckBox) item.findViewById(R.id.favorite);
			favorite.setOnClickListener(new CompoundButton.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					CellData cell = cells.get(my_position);
					cell.favorite = !cell.favorite;
					cells = CellCollection.getInstance().getGroup(cell.group);
					notifyDataSetChanged();
				}
			});
			final ViewHolder viewHolder = new ViewHolder();
			viewHolder.titleView = titleView;
			viewHolder.descriptionView = descriptionView;
			viewHolder.favorite = favorite;
			item.setTag(viewHolder);
		} else {
			item = convertView;
			ViewHolder viewHolder = (ViewHolder) convertView.getTag();
			titleView = viewHolder.titleView;
			descriptionView = viewHolder.descriptionView;
			favorite = viewHolder.favorite;
		}

		try {
			CellData cell = cells.get(my_position);
			titleView.setText(cell.title);
			descriptionView.setText(cell.description);
			favorite.setChecked(cell.isFavorite());
		} catch (IndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			titleView.setText("Uninitialized");
			descriptionView.setText("");
		}

		return item;
	}

}
